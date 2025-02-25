package dataframe.udf

import dataframe.udf.CounterUdf4.CounterUdfOps4
import dataframe.udf.CounterUdf5.CounterUdfOps5
import factory.Factory
import org.apache.spark.SparkContext
import org.apache.spark.sql.functions._
import org.apache.spark.sql.{Column, DataFrame, Row}
import org.apache.spark.util.LongAccumulator
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class IncrementAccumulatorInUdfTest extends AnyFlatSpec with Matchers {

  it should "#1 increment accumulator (one-line UDF)" in {
    val df = Factory.createDf("name STRING", Row("John"), Row("Mary"), Row(null))
    val totalAcc = Factory.sc.longAccumulator("total")
    val emptyAcc = Factory.sc.longAccumulator("empty")
    val upperCaseUdf = udf((name: String) => {
      totalAcc.add(1)
      if (name == null || name.trim.isEmpty) emptyAcc.add(1)
      name
    })
    val updatedDf = df.withColumn("name", upperCaseUdf(col("name")))
    updatedDf.toJSON.collect() should contain inOrderOnly(
      """{"name":"John"}""",
      """{"name":"Mary"}""",
      """{"name":null}""")
    totalAcc.value shouldBe 3
    emptyAcc.value shouldBe 1
  }

  it should "#2 increment accumulator (accumulators are outside of UDF class)" in {
    val df = Factory.createDf("name STRING", Row("John"), Row("Mary"), Row(null))
    val totalAcc = Factory.sc.longAccumulator("total")
    val emptyAcc = Factory.sc.longAccumulator("empty")
    val counterUdf = CounterUdf2(totalAcc, emptyAcc)
    val updatedDf = df.withColumn("name", counterUdf(col("name")))
    updatedDf.toJSON.collect() should contain inOrderOnly(
      """{"name":"John"}""",
      """{"name":"Mary"}""",
      """{"name":null}""")
    totalAcc.value shouldBe 3
    emptyAcc.value shouldBe 1
  }

  it should "#3 increment accumulator (accumulators are inside UDF class)" in {
    val df = Factory.createDf("name STRING", Row("John"), Row("Mary"), Row(null))
    val counterUdf = EmptyCounterUdf3(Factory.sc)
    val updatedDf = df.withColumn("name", counterUdf.count(col("name")))
    updatedDf.toJSON.collect() should contain inOrderOnly(
      """{"name":"John"}""",
      """{"name":"Mary"}""",
      """{"name":null}""")
    counterUdf.getTotal shouldBe 3
    counterUdf.getEmpty shouldBe 1
  }

  it should "#4 increment accumulator (accumulators are inside UDF class, implicit conversions)" in {
    val df = Factory.createDf("name STRING", Row("John"), Row("Mary"), Row(null))
    val totalAcc = Factory.sc.longAccumulator("total")
    val emptyAcc = Factory.sc.longAccumulator("empty")
    val updatedDf = df.countStrings4("name", totalAcc, emptyAcc)
    updatedDf.toJSON.collect() should contain inOrderOnly(
      """{"name":"John"}""",
      """{"name":"Mary"}""",
      """{"name":null}""")
    totalAcc.value shouldBe 3
    emptyAcc.value shouldBe 1
  }

  it should "#5 increment accumulator (accumulators are inside UDF class, implicit conversions, multi-columns)" in {
    val df = Factory.createDf("name STRING, surname STRING", Row("John", "Smith"), Row("Mary", null), Row(null, "Roger"), Row(null, null))
    val totalAcc = Factory.sc.longAccumulator("total")
    val emptyAcc = Factory.sc.longAccumulator("empty")
    val updatedDf = df.countStrings5(totalAcc, emptyAcc, "name", "surname")
    updatedDf.explain()
    val columnToExclude = "processed_columns"
    val selectedColumns = updatedDf.columns.filter(_ != columnToExclude).map(col)
    val resultDf = updatedDf.select(selectedColumns: _*)
    resultDf.explain()
    resultDf.toJSON.collect() should contain inOrderOnly(
      """{"name":"John","surname":"Smith"}""",
      """{"name":"Mary","surname":null}""",
      """{"name":null,"surname":"Roger"}""",
      """{"name":null,"surname":null}""")
    totalAcc.value shouldBe 4
    emptyAcc.value shouldBe 1
  }

}

case class CounterUdf2(totalAcc: LongAccumulator, emptyAcc: LongAccumulator) {
  def apply(stringCol: Column): Column = udf((str: String) => {
    totalAcc.add(1)
    if (str == null || str.trim.isEmpty) emptyAcc.add(1)
    str
  }).apply(stringCol)
}

case class CounterUdf3(totalAcc: LongAccumulator, emptyAcc: LongAccumulator) {
  def apply(stringCol: Column): Column = udf((str: String) => {
    totalAcc.add(1)
    if (str == null || str.trim.isEmpty) emptyAcc.add(1)
    str
  }).apply(stringCol)
}

object EmptyCounterUdf3 {
  def apply(sc: SparkContext): EmptyCounterUdf3 = new EmptyCounterUdf3(sc)
}

class EmptyCounterUdf3(sc: SparkContext) {
  private val totalAcc = sc.longAccumulator("total")
  private val emptyAcc = sc.longAccumulator("empty")

  def count(stringCol: Column): Column = CounterUdf3(totalAcc, emptyAcc).apply(stringCol)

  def getTotal: Long = totalAcc.value

  def getEmpty: Long = emptyAcc.value
}

object CounterUdf4 {
  implicit class CounterUdfOps4(val df: DataFrame) {
    def countStrings4(stringColName: String, totalAcc: LongAccumulator, emptyAcc: LongAccumulator): DataFrame = {
      df.withColumn(stringColName, udf((str: String) => {
        totalAcc.add(1)
        if (str == null || str.trim.isEmpty) emptyAcc.add(1)
        str
      }).apply(col(stringColName)))
    }
  }
}

case class CounterUdf5(totalAcc: LongAccumulator, emptyAcc: LongAccumulator) {
  def apply(columns: Seq[String]): Column = {
    val combinedColumns = array(columns.map(col): _*)
    val counterUdf = udf((values: Seq[String]) => {
      totalAcc.add(1)
      val isAllEmptyOrNull = values == null || values.forall(v => v == null || v.trim.isEmpty)
      if (isAllEmptyOrNull) emptyAcc.add(1)
      values
    })
    counterUdf(combinedColumns)
  }
}

object CounterUdf5 {
  implicit class CounterUdfOps5(val df: DataFrame) {
    def countStrings5(totalAcc: LongAccumulator, emptyAcc: LongAccumulator, columns: String*): DataFrame = {
      val counterUdf5 = CounterUdf5(totalAcc, emptyAcc)
      df.withColumn("processed_columns", counterUdf5(columns))
//        .persist //prevents excluding `withColumn` and `drop` from the execution plan
//        .drop("processed_columns")
    }
  }
}



package spark4.sql.dataframe.operation.transformation.union

import org.apache.spark.sql.functions.col
import org.apache.spark.sql.{AnalysisException, Row}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import spark4.sql.Factory

class UnionTransformationTest extends AnyFlatSpec with Matchers {

  it should "unite two DataFrames" in {
    import Factory.ss.implicits._
    val df1 = (1 to 2).toDF("numbers")
    val df2 = (5 to 6).toDF("numbers")
    val unionDf = df1.union(df2)
    unionDf.toJSON.collect should contain inOrderOnly(
      """{"numbers":1}""",
      """{"numbers":2}""",
      """{"numbers":5}""",
      """{"numbers":6}"""
    )
  }

  it should "unite different data types DataFrames" in {
    val df1 = Factory.createDf("id STRING", Row("1"), Row("2"))
    df1.schema.toDDL shouldEqual "id STRING"

    val df2 = Factory.createDf("id INT", Row(3), Row(4))
    df2.schema.toDDL shouldEqual "id INT"

    val unionDf = df1.union(df2)
    unionDf.schema.toDDL shouldEqual "id STRING"
    unionDf.toJSON.collect should contain inOrderOnly(
      """{"id":"1"}""",
      """{"id":"2"}""",
      """{"id":"3"}""",
      """{"id":"4"}"""
    )
  }

  it should "unite different column order" in {
    val df1 = Factory.createDf("id STRING, name STRING", Row("1", "John"), Row("2", "Mary"))
    val df2 = Factory.createDf("name STRING, id STRING", Row("Mark", "3"), Row("Mike", "4"))
    val unionDf = df1.union(df2)
    unionDf.toJSON.collect should contain inOrderOnly(
      """{"id":"1","name":"John"}""",
      """{"id":"2","name":"Mary"}""",
      """{"id":"Mark","name":"3"}""",
      """{"id":"Mike","name":"4"}"""
    )
  }

  it should "unite different column order (auto-reorder)" in {
    val df1 = Factory.createDf("id STRING, name STRING", Row("1", "John"), Row("2", "Mary"))
    val df2 = Factory.createDf("name STRING, id STRING", Row("Mark", "3"), Row("Mike", "4"))
    val df2Reordered = df2.select(df1.columns.map(col): _*)
    val unionDf = df1.union(df2Reordered)
    unionDf.toJSON.collect should contain inOrderOnly(
      """{"id":"1","name":"John"}""",
      """{"id":"2","name":"Mary"}""",
      """{"id":"3","name":"Mark"}""",
      """{"id":"4","name":"Mike"}"""
    )
  }

  it should "fail with different column number" in {
    val df1 = Factory.createDf("id STRING, name STRING", Row("1", "John"))
    val df2 = Factory.createDf("id STRING", Row("2"))
    val e = intercept[AnalysisException] {
      df1.union(df2)
    }
    e.getMessage should include("[NUM_COLUMNS_MISMATCH] " +
      "UNION can only be performed on inputs with the same number of columns, " +
      "but the first input has 2 columns and the second input has 1 columns.")
  }

  it should "unite a struct column" in {
    val tdd = "id STRING, details STRUCT<name:STRING, age:INT>"
    val df1 = Factory.createDf(tdd, Row("1", Row("John", 30)), Row("2", Row("Mary", 25)))
    val df2 = Factory.createDf(tdd, Row("3", Row("Mark", 20)))
    val unionDf = df1.union(df2)
    unionDf.toJSON.collect should contain inOrderOnly(
      """{"id":"1","details":{"name":"John","age":30}}""",
      """{"id":"2","details":{"name":"Mary","age":25}}""",
      """{"id":"3","details":{"name":"Mark","age":20}}"""
    )
  }

}
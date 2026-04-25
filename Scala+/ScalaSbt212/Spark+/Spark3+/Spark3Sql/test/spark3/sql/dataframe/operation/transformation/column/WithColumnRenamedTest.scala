package spark3.sql.dataframe.operation.transformation.column

import org.apache.spark.sql.functions.{col, lit}
import org.apache.spark.sql.{AnalysisException, DataFrame}
import org.scalatest.flatspec.AnyFlatSpec
import spark3.sql.{Factory, SparkMatchers}

class WithColumnRenamedTest extends AnyFlatSpec with SparkMatchers {

  it should "rename a column" in {
    val df: DataFrame = Factory.peopleDf
    df shouldHaveDDL "name STRING,age INT,gender STRING"
    val updatedDf: DataFrame = df.withColumnRenamed("name", "fio")
    updatedDf shouldHaveDDL "fio STRING,age INT,gender STRING"
  }

  it should "rename an ambiguous column" in {
    val df = Factory.peopleDf
    df shouldHaveDDL "name STRING,age INT,gender STRING"
    val df2 = df.select(col("name"), col("age"), col("gender"), lit("abc").as("name"))
    df2 shouldHaveDDL "name STRING,age INT,gender STRING,name STRING NOT NULL"
    df2 shouldContain(
      """{"name":"John","age":25,"gender":"M","name":"abc"}""",
      """{"name":"Peter","age":35,"gender":"M","name":"abc"}""",
      """{"name":"Mary","age":20,"gender":"F","name":"abc"}""")

    val e = the[AnalysisException] thrownBy {
      df2.select("name")
    }
    e.getMessage() shouldEqual "[AMBIGUOUS_REFERENCE] Reference `name` is ambiguous, could be: [`name`, `name`]."
  }

  ignore should "rename a nested column" in {
    // Dataset#withColumnRenamed supports only top-level columns.
    // See "rename a nested column" in UpdateColumnTest
  }

}
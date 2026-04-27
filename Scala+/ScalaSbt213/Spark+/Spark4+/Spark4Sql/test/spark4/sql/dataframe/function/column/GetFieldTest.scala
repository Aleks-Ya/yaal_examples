package spark4.sql.dataframe.function.column

import org.apache.spark.sql.Row
import org.apache.spark.sql.functions.col
import org.scalatest.flatspec.AnyFlatSpec
import spark4.sql.{Factory, SparkMatchers}

class GetFieldTest extends AnyFlatSpec with SparkMatchers {

  import Factory.ss.implicits._

  private val df = Factory.createDf("name STRING,info STRUCT<age: INT,gender: STRING>",
    Row("John", Row(30, "M")),
    Row("Mary", Row(null, "F")),
    Row("Mark", null))

  it should "refer a sub-field (getField)" in {
    val updatedDf = df.select(
      col("name"),
      col("info").getField("age"),
      $"info".getField("age"))
    updatedDf shouldHaveDDL "name STRING,`info.age` INT,`info.age` INT"
    updatedDf shouldContain(
      """{"name":"John","info.age":30,"info.age":30}""",
      """{"name":"Mary","info.age":null,"info.age":null}""",
      """{"name":"Mark","info.age":null,"info.age":null}""")
  }

  it should "refer a sub-field (dot)" in {
    val updatedDf = df.select(
      col("name"),
      col("info.age"),
      $"info.age")
    updatedDf shouldHaveDDL "name STRING,age INT,age INT"
    updatedDf shouldContain(
      """{"name":"John","age":30,"age":30}""",
      """{"name":"Mary","age":null,"age":null}""",
      """{"name":"Mark","age":null,"age":null}""")
  }

  it should "refer a sub-field (apply)" in {
    val updatedDf = df.select(
      col("name"),
      col("info")("age"),
      $"info"("age"))
    updatedDf shouldHaveDDL "name STRING,`info.age` INT,`info.age` INT"
    updatedDf shouldContain(
      """{"name":"John","info.age":30,"info.age":30}""",
      """{"name":"Mary","info.age":null,"info.age":null}""",
      """{"name":"Mark","info.age":null,"info.age":null}""")
  }

}
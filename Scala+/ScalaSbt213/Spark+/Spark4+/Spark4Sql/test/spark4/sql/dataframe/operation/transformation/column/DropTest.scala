package spark4.sql.dataframe.operation.transformation.column

import org.apache.spark.sql.Row
import org.apache.spark.sql.functions.{col, struct, transform}
import org.scalatest.flatspec.AnyFlatSpec
import spark4.sql.{Factory, SparkMatchers}

class DropTest extends AnyFlatSpec with SparkMatchers {

  it should "delete an existing column" in {
    val df = Factory.peopleDf
    df shouldHaveDDL "name STRING,age INT,gender STRING"
    val updatedDf = df.drop("gender")
    updatedDf shouldHaveDDL "name STRING,age INT"
  }

  it should "delete an absent column" in {
    val df = Factory.peopleDf
    df shouldHaveDDL "name STRING,age INT,gender STRING"
    val updatedDf = df.drop("absent_column")
    updatedDf shouldHaveDDL "name STRING,age INT,gender STRING"
  }

  it should "delete a column in each element of an array (way 1)" in {
    val df = Factory.createDf("city STRING,people ARRAY<STRUCT<name: STRING,age: INT>>",
      Row("London", List(Row("John", 35), Row("Mary", 30))),
      Row("Berlin", List(Row("Mark", 25), Row("Matt", 20))))
    val updatedDf = df.select(
      col("city"),
      transform(col("people"), peopleCol => peopleCol.dropFields("age")) as "people_dropped"
    )
    updatedDf shouldHaveDDL "city STRING,people_dropped ARRAY<STRUCT<name: STRING>>"
    updatedDf shouldContain(
      """{"city":"London","people_dropped":[{"name":"John"},{"name":"Mary"}]}""",
      """{"city":"Berlin","people_dropped":[{"name":"Mark"},{"name":"Matt"}]}"""
    )
  }

  it should "delete a column in each element of an array (way 2)" in {
    val df = Factory.createDf("city STRING,people ARRAY<STRUCT<name: STRING,age: INT>>",
      Row("London", List(Row("John", 35), Row("Mary", 30))),
      Row("Berlin", List(Row("Mark", 25), Row("Matt", 20))))
    val updatedDf = df.select(
      col("city"),
      transform(col("people"), peopleCol => struct(peopleCol("name") as "name")) as "people_dropped"
    )
    updatedDf shouldHaveDDL "city STRING,people_dropped ARRAY<STRUCT<name: STRING>>"
    updatedDf shouldContain(
      """{"city":"London","people_dropped":[{"name":"John"},{"name":"Mary"}]}""",
      """{"city":"Berlin","people_dropped":[{"name":"Mark"},{"name":"Matt"}]}"""
    )
  }

}
package dataframe.transformation.column

import factory.Factory
import org.apache.spark.sql.Row
import org.apache.spark.sql.functions.{col, struct, transform}
import org.apache.spark.sql.types.{ArrayType, IntegerType, StringType, StructField, StructType}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class DropColumnTest extends AnyFlatSpec with Matchers {

  it should "delete a column" in {
    val df = Factory.peopleDf
    df.schema.simpleString shouldEqual "struct<name:string,age:int,gender:string>"
    val updatedDf = df.drop("gender")
    updatedDf.schema.simpleString shouldEqual "struct<name:string,age:int>"
  }

  it should "delete a column in each element of an array (way 1)" in {
    val df = Factory.createDf(Map("city" -> StringType, "people" -> ArrayType(
      StructType(Seq(
        StructField("name", StringType),
        StructField("age", IntegerType)))
    )),
      Row("London", List(Row("John", 35), Row("Mary", 30))),
      Row("Berlin", List(Row("Mark", 25), Row("Matt", 20))))
    val updatedDf = df.select(
      col("city"),
      transform(col("people"), peopleCol => peopleCol.dropFields("age")) as "people_dropped"
    )
    updatedDf.toJSON.collect should contain inOrderOnly(
      """{"city":"London","people_dropped":[{"name":"John"},{"name":"Mary"}]}""",
      """{"city":"Berlin","people_dropped":[{"name":"Mark"},{"name":"Matt"}]}"""
    )
  }

  it should "delete a column in each element of an array (way 2)" in {
    val df = Factory.createDf(Map("city" -> StringType, "people" -> ArrayType(
      StructType(Seq(
        StructField("name", StringType),
        StructField("age", IntegerType)))
    )),
      Row("London", List(Row("John", 35), Row("Mary", 30))),
      Row("Berlin", List(Row("Mark", 25), Row("Matt", 20))))
    val updatedDf = df.select(
      col("city"),
      transform(col("people"), peopleCol => struct(peopleCol("name") as "name")) as "people_dropped"
    )
    updatedDf.toJSON.collect should contain inOrderOnly(
      """{"city":"London","people_dropped":[{"name":"John"},{"name":"Mary"}]}""",
      """{"city":"Berlin","people_dropped":[{"name":"Mark"},{"name":"Matt"}]}"""
    )
  }

}
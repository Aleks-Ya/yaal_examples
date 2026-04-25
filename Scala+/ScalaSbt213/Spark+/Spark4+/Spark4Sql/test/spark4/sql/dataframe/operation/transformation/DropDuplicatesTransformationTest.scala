package spark4.sql.dataframe.operation.transformation

import org.apache.spark.sql.Row
import org.apache.spark.sql.types.StringType
import org.scalatest.flatspec.AnyFlatSpec
import spark4.sql.{Factory, SparkMatchers}

class DropDuplicatesTransformationTest extends AnyFlatSpec with SparkMatchers {

  it should "delete rows having the same value in a single field" in {
    val df = Factory.createDf(Map("name" -> StringType, "department" -> StringType),
      Row("John", "IT"), Row("Peter", "Sales"), Row("Mary", "IT"))
    df shouldContain(
      """{"name":"John","department":"IT"}""",
      """{"name":"Peter","department":"Sales"}""",
      """{"name":"Mary","department":"IT"}"""
    )
    val updatedDf = df.dropDuplicates("department")
    updatedDf shouldContain(
      """{"name":"John","department":"IT"}""",
      """{"name":"Peter","department":"Sales"}"""
    )
  }

  it should "delete rows having the same value in multiple fields" in {
    val df = Factory.createDf(Map("name" -> StringType, "department" -> StringType, "city" -> StringType),
      Row("John", "IT", "London"), Row("Peter", "Sales", "Berlin"), Row("Mary", "IT", "London"))
    df shouldContain(
      """{"name":"John","department":"IT","city":"London"}""",
      """{"name":"Peter","department":"Sales","city":"Berlin"}""",
      """{"name":"Mary","department":"IT","city":"London"}"""
    )
    val updatedDf = df.dropDuplicates("department", "city")
    updatedDf shouldContain(
      """{"name":"John","department":"IT","city":"London"}""",
      """{"name":"Peter","department":"Sales","city":"Berlin"}"""
    )
  }

  it should "delete rows having the same value in all fields" in {
    val df = Factory.createDf(Map("name" -> StringType, "department" -> StringType),
      Row("John", "IT"), Row("John", "Sales"), Row("John", "IT"))
    df.toJSON.collect should contain theSameElementsInOrderAs Seq(
      """{"name":"John","department":"IT"}""",
      """{"name":"John","department":"Sales"}""",
      """{"name":"John","department":"IT"}"""
    )
    val updatedDf = df.dropDuplicates()
    updatedDf shouldContain(
      """{"name":"John","department":"Sales"}""",
      """{"name":"John","department":"IT"}"""
    )
  }
}
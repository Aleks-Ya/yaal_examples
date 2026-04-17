package spark4.sql.dataframe.operation.transformation

import org.apache.spark.sql.Row
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import spark4.sql.Factory

class DistinctTransformationTest extends AnyFlatSpec with Matchers {

  it should "distinct primitive columns" in {
    val df = Factory.createDf("name STRING, age INT",
      Row("John", 35),
      Row("John", 25),
      Row("John", 25),
      Row("Peter", null),
      Row("Peter", null),
      Row("Mary", 20),
      Row(null, null),
      Row(null, null))
    val updatedDf = df.distinct
    updatedDf.toJSON.collect should contain inOrderOnly(
      """{"name":"John","age":35}""",
      """{"name":"John","age":25}""",
      """{"name":"Mary","age":20}""",
      """{"name":null,"age":null}""",
      """{"name":"Peter","age":null}"""
    )
  }

  it should "distinct struct columns" in {
    val df = Factory.createDf("name STRING, details STRUCT<age:INT, gender:STRING>",
      Row("John", Row(35, "M")),
      Row("John", Row(25, "M")),
      Row("John", Row(25, "M")),
      Row("Peter", null),
      Row("Peter", null),
      Row("Mary", Row(20, "F")),
      Row(null, null),
      Row(null, null),
      Row(null, Row(null, null)),
      Row(null, Row(null, null)))
    val updatedDf = df.distinct
    updatedDf.toJSON.collect should contain inOrderOnly(
      """{"name":"Mary","details":{"age":20,"gender":"F"}}""",
      """{"name":null,"details":null}""",
      """{"name":null,"details":{"age":null,"gender":null}}""",
      """{"name":"John","details":{"age":35,"gender":"M"}}""",
      """{"name":"Peter","details":null}""",
      """{"name":"John","details":{"age":25,"gender":"M"}}"""
    )
  }

}
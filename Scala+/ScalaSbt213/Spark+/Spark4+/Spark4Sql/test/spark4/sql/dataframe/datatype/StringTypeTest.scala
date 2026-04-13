package spark4.sql.dataframe.datatype

import org.apache.spark.sql.Row
import org.apache.spark.sql.types.StringType
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import spark4.sql.Factory

class StringTypeTest extends AnyFlatSpec with Matchers {

  it should "use STRING column type" in {
    val df = Factory.createDf("name STRING, city STRING",
      Row("John", "London"),
      Row("Mary", "Paris"))
    df.toJSON.collect should contain inOrderOnly(
      """{"name":"John","city":"London"}""",
      """{"name":"Mary","city":"Paris"}""")
  }

  it should "StringType column type" in {
    val df = Factory.createDf(Map("name" -> StringType, "city" -> StringType),
      Row("John", "London"),
      Row("Mary", "Paris"))
    df.schema.toDDL shouldEqual "name STRING,city STRING"
    df.toJSON.collect should contain inOrderOnly(
      """{"name":"John","city":"London"}""",
      """{"name":"Mary","city":"Paris"}""")
  }

}
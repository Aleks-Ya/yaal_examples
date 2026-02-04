package dataframe.datatype

import factory.Factory
import org.apache.spark.sql.Row
import org.apache.spark.sql.types.{BinaryType, StringType}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class BinaryTypeTest extends AnyFlatSpec with Matchers {

  it should "BINARY column type" in {
    val df = Factory.createDf("name STRING, content BINARY",
      Row("John", "content".getBytes),
      Row("Mary", "data".getBytes))
    df.toJSON.collect should contain inOrderOnly(
      """{"name":"John","content":"Y29udGVudA=="}""",
      """{"name":"Mary","content":"ZGF0YQ=="}""")
  }

  it should "IntegerType column type" in {
    val df = Factory.createDf(Map("name" -> StringType, "content" -> BinaryType),
      Row("John", "content".getBytes),
      Row("Mary", "data".getBytes))
    df.toJSON.collect should contain inOrderOnly(
      """{"name":"John","content":"Y29udGVudA=="}""",
      """{"name":"Mary","content":"ZGF0YQ=="}""")
  }

}
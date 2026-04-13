package spark3.sql.dataframe.datatype

import org.apache.spark.sql.Row
import org.apache.spark.sql.types.{BinaryType, StringType}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import spark3.sql.Factory

class BinaryTypeTest extends AnyFlatSpec with Matchers {

  it should "use BINARY column type" in {
    val df = Factory.createDf("name STRING, content BINARY",
      Row("John", "content".getBytes),
      Row("Mary", "data".getBytes))
    df.toJSON.collect should contain inOrderOnly(
      """{"name":"John","content":"Y29udGVudA=="}""",
      """{"name":"Mary","content":"ZGF0YQ=="}""")
  }

  it should "use BinaryType column type" in {
    val df = Factory.createDf(Map("name" -> StringType, "content" -> BinaryType),
      Row("John", "content".getBytes),
      Row("Mary", "data".getBytes))
    df.schema.toDDL shouldEqual "name STRING,content BINARY"
    df.toJSON.collect should contain inOrderOnly(
      """{"name":"John","content":"Y29udGVudA=="}""",
      """{"name":"Mary","content":"ZGF0YQ=="}""")
  }

}
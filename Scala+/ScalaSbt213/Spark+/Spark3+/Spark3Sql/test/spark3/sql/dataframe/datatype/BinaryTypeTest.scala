package spark3.sql.dataframe.datatype

import org.apache.spark.sql.Row
import org.apache.spark.sql.types.{BinaryType, StringType}
import org.scalatest.flatspec.AnyFlatSpec
import spark3.sql.{Factory, SparkMatchers}

class BinaryTypeTest extends AnyFlatSpec with SparkMatchers {

  it should "use BINARY column type" in {
    val df = Factory.createDf("name STRING, content BINARY",
      Row("John", "content".getBytes),
      Row("Mary", "data".getBytes))
    df shouldContain(
      """{"name":"John","content":"Y29udGVudA=="}""",
      """{"name":"Mary","content":"ZGF0YQ=="}""")
  }

  it should "use BinaryType column type" in {
    val df = Factory.createDf(Map("name" -> StringType, "content" -> BinaryType),
      Row("John", "content".getBytes),
      Row("Mary", "data".getBytes))
    df shouldHaveDDL "name STRING,content BINARY"
    df shouldContain(
      """{"name":"John","content":"Y29udGVudA=="}""",
      """{"name":"Mary","content":"ZGF0YQ=="}""")
  }

}
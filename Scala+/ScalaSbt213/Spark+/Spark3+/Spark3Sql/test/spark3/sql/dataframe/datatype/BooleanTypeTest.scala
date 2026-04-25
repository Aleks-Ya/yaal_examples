
package spark3.sql.dataframe.datatype

import org.apache.spark.sql.Row
import org.apache.spark.sql.types.{BooleanType, StringType}
import org.scalatest.flatspec.AnyFlatSpec
import spark3.sql.{Factory, SparkMatchers}

class BooleanTypeTest extends AnyFlatSpec with SparkMatchers {

  it should "use BOOLEAN column type" in {
    val df = Factory.createDf("name STRING, employed BOOLEAN",
      Row("John", true),
      Row("Mary", false),
      Row("Mark", null))
    df shouldContain(
      """{"name":"John","employed":true}""",
      """{"name":"Mary","employed":false}""",
      """{"name":"Mark","employed":null}""")
  }

  it should "use BooleanType column type" in {
    val df = Factory.createDf(Map("name" -> StringType, "employed" -> BooleanType),
      Row("John", true),
      Row("Mary", false),
      Row("Mark", null)
    )
    df shouldHaveDDL "name STRING,employed BOOLEAN"
    df shouldContain(
      """{"name":"John","employed":true}""",
      """{"name":"Mary","employed":false}""",
      """{"name":"Mark","employed":null}""")
  }

}
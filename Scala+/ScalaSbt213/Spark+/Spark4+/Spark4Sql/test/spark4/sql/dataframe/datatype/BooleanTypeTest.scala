
package spark4.sql.dataframe.datatype

import org.apache.spark.sql.Row
import org.apache.spark.sql.types.{BooleanType, StringType}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import spark4.sql.Factory

class BooleanTypeTest extends AnyFlatSpec with Matchers {

  it should "use BOOLEAN column type" in {
    val df = Factory.createDf("name STRING, employed BOOLEAN",
      Row("John", true),
      Row("Mary", false),
      Row("Mark", null))
    df.toJSON.collect should contain inOrderOnly(
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
    df.schema.toDDL shouldEqual "name STRING,employed BOOLEAN"
    df.toJSON.collect should contain inOrderOnly(
      """{"name":"John","employed":true}""",
      """{"name":"Mary","employed":false}""",
      """{"name":"Mark","employed":null}""")
  }

}
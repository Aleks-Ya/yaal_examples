package spark4.sql.dataframe.datatype

import org.apache.spark.sql.Row
import org.apache.spark.sql.types.{LongType, StringType}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import spark4.sql.Factory

class LongTypeTest extends AnyFlatSpec with Matchers {

  it should "BIGINT column type" in {
    val df = Factory.createDf("name STRING, weight BIGINT",
      Row("John", 90L),
      Row("Mary", 45L))
    df.toJSON.collect should contain inOrderOnly(
      """{"name":"John","weight":90}""",
      """{"name":"Mary","weight":45}""")
  }

  it should "LongType column type" in {
    val df = Factory.createDf(Map("name" -> StringType, "weight" -> LongType),
      Row("John", 90L),
      Row("Mary", 45L))
    df.schema.toDDL shouldEqual "name STRING,weight BIGINT"
    df.toJSON.collect should contain inOrderOnly(
      """{"name":"John","weight":90}""",
      """{"name":"Mary","weight":45}""")
  }

}
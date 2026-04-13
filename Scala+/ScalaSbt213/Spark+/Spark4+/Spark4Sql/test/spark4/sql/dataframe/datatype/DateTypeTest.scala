package spark4.sql.dataframe.datatype

import org.apache.spark.sql.Row
import org.apache.spark.sql.types.{DateType, StringType}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import spark4.sql.Factory

import java.sql.Date

class DateTypeTest extends AnyFlatSpec with Matchers {

  it should "use DATE column type" in {
    val df = Factory.createDf("country STRING, visit DATE",
      Row("USA", Date.valueOf("2023-10-05")),
      Row("Canada", Date.valueOf("2024-01-30")))
    df.toJSON.collect should contain inOrderOnly(
      """{"country":"USA","visit":"2023-10-05"}""",
      """{"country":"Canada","visit":"2024-01-30"}"""
    )
  }

  it should "use DateType" in {
    val df = Factory.createDf(Map("country" -> StringType, "visit" -> DateType),
      Row("USA", Date.valueOf("2023-10-05")),
      Row("Canada", Date.valueOf("2024-01-30")))
    df.schema.toDDL shouldEqual "country STRING,visit DATE"
    df.toJSON.collect should contain inOrderOnly(
      """{"country":"USA","visit":"2023-10-05"}""",
      """{"country":"Canada","visit":"2024-01-30"}"""
    )
  }

}
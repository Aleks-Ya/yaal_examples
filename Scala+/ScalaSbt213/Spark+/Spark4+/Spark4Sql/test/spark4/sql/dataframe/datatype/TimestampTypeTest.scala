package spark4.sql.dataframe.datatype

import org.apache.spark.sql.Row
import org.apache.spark.sql.types.{StringType, TimestampType}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import spark4.sql.Factory

import java.sql.Timestamp

class TimestampTypeTest extends AnyFlatSpec with Matchers {

  it should "use TIMESTAMP column type" in {
    val df = Factory.createDf("country STRING, visit TIMESTAMP",
      Row("USA", Timestamp.valueOf("2023-10-05 14:40:25")),
      Row("Canada", Timestamp.valueOf("2024-01-30 16:50:35")))
    df.toJSON.collect should contain inOrderOnly(
      """{"country":"USA","visit":"2023-10-05T14:40:25.000+07:00"}""",
      """{"country":"Canada","visit":"2024-01-30T16:50:35.000+07:00"}"""
    )
  }

  it should "use TimestampType" in {
    val df = Factory.createDf(Map("country" -> StringType, "visit" -> TimestampType),
      Row("USA", Timestamp.valueOf("2023-10-05 14:40:25")),
      Row("Canada", Timestamp.valueOf("2024-01-30 16:50:35")))
    df.schema.toDDL shouldEqual "country STRING,visit TIMESTAMP"
    df.toJSON.collect should contain inOrderOnly(
      """{"country":"USA","visit":"2023-10-05T14:40:25.000+07:00"}""",
      """{"country":"Canada","visit":"2024-01-30T16:50:35.000+07:00"}"""
    )
  }
  
}
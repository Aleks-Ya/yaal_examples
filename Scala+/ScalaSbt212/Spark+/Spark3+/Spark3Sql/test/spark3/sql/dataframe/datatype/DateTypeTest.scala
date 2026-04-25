package spark3.sql.dataframe.datatype

import org.apache.spark.sql.Row
import org.apache.spark.sql.types.{DateType, StringType}
import org.scalatest.flatspec.AnyFlatSpec
import spark3.sql.{Factory, SparkMatchers}

import java.sql.Date

class DateTypeTest extends AnyFlatSpec with SparkMatchers {

  it should "use DATE column type" in {
    val df = Factory.createDf("country STRING, visit DATE",
      Row("USA", Date.valueOf("2023-10-05")),
      Row("Canada", Date.valueOf("2024-01-30")))
    df shouldContain(
      """{"country":"USA","visit":"2023-10-05"}""",
      """{"country":"Canada","visit":"2024-01-30"}"""
    )
  }

  it should "use DateType" in {
    val df = Factory.createDf(Map("country" -> StringType, "visit" -> DateType),
      Row("USA", Date.valueOf("2023-10-05")),
      Row("Canada", Date.valueOf("2024-01-30")))
    df shouldHaveDDL "country STRING,visit DATE"
    df shouldContain(
      """{"country":"USA","visit":"2023-10-05"}""",
      """{"country":"Canada","visit":"2024-01-30"}"""
    )
  }

}
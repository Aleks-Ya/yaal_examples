package spark4.sql.dataframe.predicate

import org.apache.spark.sql.functions.col
import org.scalatest.flatspec.AnyFlatSpec
import spark4.sql.{Factory, SparkMatchers}

class GreaterThanTest extends AnyFlatSpec with SparkMatchers {

  it should "use greater than symbol" in {
    val df = Factory.peopleDf
    val updatedDf = df.filter(col("age") > 20)
    updatedDf shouldContain(
      """{"name":"John","age":25,"gender":"M"}""",
      """{"name":"Peter","age":35,"gender":"M"}""")
  }

  it should "use greater than method" in {
    val df = Factory.peopleDf
    val updatedDf = df.filter(col("age").gt(20))
    updatedDf shouldContain(
      """{"name":"John","age":25,"gender":"M"}""",
      """{"name":"Peter","age":35,"gender":"M"}""")
  }

}
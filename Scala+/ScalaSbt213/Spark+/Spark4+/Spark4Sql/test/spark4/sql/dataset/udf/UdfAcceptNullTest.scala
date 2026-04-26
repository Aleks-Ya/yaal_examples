package spark4.sql.dataset.udf

import org.apache.spark.sql.Column
import org.apache.spark.sql.functions._
import org.scalatest.flatspec.AnyFlatSpec
import spark4.sql.{Factory, SparkMatchers}

class UdfAcceptNullTest extends AnyFlatSpec with SparkMatchers {

  it should "accepts null value" in {
    val df1 = Factory.peopleDf.withColumn("isAdult", IsAdultOptionUdf(col("age")))
    df1 shouldContain(
      """{"name":"John","age":25,"gender":"M","isAdult":true}""",
      """{"name":"Peter","age":35,"gender":"M","isAdult":true}""",
      """{"name":"Mary","age":20,"gender":"F","isAdult":true}"""
    )

    val df2 = Factory.peopleDf.withColumn("isAdult", IsAdultUdf(col("age")))
    df2 shouldContain(
      """{"name":"John","age":25,"gender":"M","isAdult":true}""",
      """{"name":"Peter","age":35,"gender":"M","isAdult":true}""",
      """{"name":"Mary","age":20,"gender":"F","isAdult":true}"""
    )
  }

  it should "not accept null column" in {
    assertThrows[NullPointerException] {
      Factory.peopleDf.withColumn("isAdult", IsAdultOptionUdf(null))
    }

    assertThrows[NullPointerException] {
      Factory.peopleDf.withColumn("isAdult", IsAdultUdf(null))
    }
  }

  it should "accepts null column" in {
    val df1 = Factory.peopleDf.withColumn("isAdult", IsAdultOptionUdf(lit(null)))
    df1 shouldContain(
      """{"name":"John","age":25,"gender":"M","isAdult":false}""",
      """{"name":"Peter","age":35,"gender":"M","isAdult":false}""",
      """{"name":"Mary","age":20,"gender":"F","isAdult":false}"""
    )

    val df2 = Factory.peopleDf.withColumn("isAdult", IsAdultUdf(lit(null)))
    df2 shouldContain(
      """{"name":"John","age":25,"gender":"M","isAdult":null}""",
      """{"name":"Peter","age":35,"gender":"M","isAdult":null}""",
      """{"name":"Mary","age":20,"gender":"F","isAdult":null}"""
    )
  }

  object IsAdultOptionUdf extends Serializable {
    def apply(ageCol: Column): Column =
      udf((age: Option[Int]) =>
        age match {
          case Some(age) => age >= 18
          case None => false
        }
      ).apply(ageCol)
  }

  object IsAdultUdf extends Serializable {
    def apply(ageCol: Column): Column = udf((age: Int) => age >= 18).apply(ageCol)
  }
}
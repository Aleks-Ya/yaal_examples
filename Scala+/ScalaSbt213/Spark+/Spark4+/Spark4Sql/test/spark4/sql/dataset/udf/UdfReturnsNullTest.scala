package spark4.sql.dataset.udf

import org.apache.spark.sql.Column
import org.apache.spark.sql.functions._
import org.scalatest.flatspec.AnyFlatSpec
import spark4.sql.{Factory, SparkMatchers}

class UdfReturnsNullTest extends AnyFlatSpec with SparkMatchers {

  it should "return null" in {
    val df = Factory.peopleDf.withColumn("seq", ToNullUdf(col("name"), col("age")))
    df shouldContain(
      """{"name":"John","age":25,"gender":"M","seq":["JOHN","25"]}""",
      """{"name":"Peter","age":35,"gender":"M","seq":null}""",
      """{"name":"Mary","age":20,"gender":"F","seq":["MARY","20"]}"""
    )
  }

  object ToNullUdf extends Serializable {
    def apply(nameCol: Column, ageCol: Column): Column =
      udf((name: String, age: Int) =>
        if ("Peter".equals(name)) null else Seq(name.toUpperCase, age.toString)
      ).apply(nameCol, ageCol)
  }
}
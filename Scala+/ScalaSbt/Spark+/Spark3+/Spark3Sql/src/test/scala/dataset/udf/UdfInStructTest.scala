package dataset.udf

import factory.Factory
import org.apache.spark.sql.Column
import org.apache.spark.sql.functions._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class UdfInStructTest extends AnyFlatSpec with Matchers {

  it should "use UDF variable in a struct" in {
    val upper: (String, Int) => String = (name: String, age: Int) => s"${name.toUpperCase}-$age"
    val upperUdf = udf(upper: (String, Int) => String)
    val df = Factory.peopleDf.select(
      col("name"),
      struct(
        col("gender").as("sex"),
        col("age").as("lifetime"),
        upperUdf(col("name"), col("age")).as("upper")
      ).as("details")
    )
    df.toJSON.collect() should contain inOrderOnly(
      """{"name":"John","details":{"sex":"M","lifetime":25,"upper":"JOHN-25"}}""",
      """{"name":"Peter","details":{"sex":"M","lifetime":35,"upper":"PETER-35"}}""",
      """{"name":"Mary","details":{"sex":"F","lifetime":20,"upper":"MARY-20"}}"""
    )
  }

  it should "use UDF field object in a struct" in {
    val df = Factory.peopleDf.select(
      col("name"),
      struct(
        col("gender").as("sex"),
        col("age").as("lifetime"),
        UpperUdfField(col("name"), col("age")).as("upper")
      ).as("details")
    )
    df.toJSON.collect() should contain inOrderOnly(
      """{"name":"John","details":{"sex":"M","lifetime":25,"upper":"JOHN-25"}}""",
      """{"name":"Peter","details":{"sex":"M","lifetime":35,"upper":"PETER-35"}}""",
      """{"name":"Mary","details":{"sex":"F","lifetime":20,"upper":"MARY-20"}}"""
    )
  }

  object UpperUdfField extends Serializable {
    private val upper: (String, Int) => String = (name: String, age: Int) => s"${name.toUpperCase}-$age"

    def apply(nameCol: Column, ageCol: Column): Column = udf(upper).apply(nameCol, ageCol)
  }

  it should "use UDF function object in a struct" in {
    val df = Factory.peopleDf.select(
      col("name"),
      struct(
        col("gender").as("sex"),
        col("age").as("lifetime"),
        UpperUdfFunction(col("name"), col("age")).as("upper")
      ).as("details")
    )
    df.toJSON.collect() should contain inOrderOnly(
      """{"name":"John","details":{"sex":"M","lifetime":25,"upper":"JOHN-25"}}""",
      """{"name":"Peter","details":{"sex":"M","lifetime":35,"upper":"PETER-35"}}""",
      """{"name":"Mary","details":{"sex":"F","lifetime":20,"upper":"MARY-20"}}"""
    )
  }

  object UpperUdfFunction extends Serializable {
    private def upper(name: String, age: Int): String = s"${name.toUpperCase}-$age"

    def apply(nameCol: Column, ageCol: Column): Column = udf(upper _).apply(nameCol, ageCol)
  }

}
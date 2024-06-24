package dataset.udf

import factory.Factory
import org.apache.spark.sql.Column
import org.apache.spark.sql.functions._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class UdfReturnsStructTest extends AnyFlatSpec with Matchers {

  it should "return a struct (generated names _1 and _2)" in {
    val df = Factory.peopleDf.withColumn("upper", UpperUdf(col("name"), col("age")))
    df.toJSON.collect() should contain inOrderOnly(
      """{"name":"John","age":25,"gender":"M","upper":{"_1":"JOHN","_2":50}}""",
      """{"name":"Peter","age":35,"gender":"M","upper":{"_1":"PETER","_2":70}}""",
      """{"name":"Mary","age":20,"gender":"F","upper":{"_1":"MARY","_2":40}}"""
    )
  }

  it should "return a struct (custom names)" in {
    val df = Factory.peopleDf.withColumn("upper", UpperUdf(col("name"), col("age")))
      .withColumn("upper", col("upper")
        .withField("upper_name", col("upper._1"))
        .withField("double_age", col("upper._2"))
        .dropFields("_1")
        .dropFields("_2"))
    df.toJSON.collect() should contain inOrderOnly(
      """{"name":"John","age":25,"gender":"M","upper":{"upper_name":"JOHN","double_age":50}}""",
      """{"name":"Peter","age":35,"gender":"M","upper":{"upper_name":"PETER","double_age":70}}""",
      """{"name":"Mary","age":20,"gender":"F","upper":{"upper_name":"MARY","double_age":40}}"""
    )
  }

  object UpperUdf extends Serializable {
    def apply(nameCol: Column, ageCol: Column): Column =
      udf((name: String, age: Int) => (name.toUpperCase, age * 2))
        .apply(nameCol, ageCol)
        .as("upper")
  }

}
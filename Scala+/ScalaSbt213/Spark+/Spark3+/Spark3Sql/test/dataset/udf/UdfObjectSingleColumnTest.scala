package dataset.udf

import factory.Factory
import factory.Factory.ss.implicits._
import org.apache.spark.sql.Column
import org.apache.spark.sql.functions._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

/**
 * UDF as a separated object (single column).
 */
class UdfObjectSingleColumnTest extends AnyFlatSpec with Matchers {

  it should "extract UDF into separated object (logic is inlined)" in {
    val df = Factory.peopleDf.withColumn("upper", UpperUdfInline($"name", $"age"))
    df.toJSON.collect should contain inOrderOnly(
      """{"name":"John","age":25,"gender":"M","upper":"JOHN-25"}""",
      """{"name":"Peter","age":35,"gender":"M","upper":"PETER-35"}""",
      """{"name":"Mary","age":20,"gender":"F","upper":"MARY-20"}"""
    )
  }

  object UpperUdfInline extends Serializable {
    def apply(name: Column, age: Column): Column =
      udf((name: String, age: Int) => s"${name.toUpperCase}-$age")
        .apply(name, age)
  }

  it should "extract UDF into separated object (logic in a field)" in {
    val df = Factory.peopleDf.withColumn("upper", UpperUdfField($"name", $"age"))
    df.toJSON.collect should contain inOrderOnly(
      """{"name":"John","age":25,"gender":"M","upper":"JOHN-25"}""",
      """{"name":"Peter","age":35,"gender":"M","upper":"PETER-35"}""",
      """{"name":"Mary","age":20,"gender":"F","upper":"MARY-20"}"""
    )
  }

  object UpperUdfField extends Serializable {
    private val upper: (String, Int) => String = (name: String, age: Int) => s"${name.toUpperCase}-$age"

    def apply(name: Column, age: Column): Column = udf(upper).apply(name, age)
  }

  it should "extract UDF into separated object (logic in a function)" in {
    val df = Factory.peopleDf.withColumn("upper", UpperUdfFunction($"name", $"age"))
    df.toJSON.collect should contain inOrderOnly(
      """{"name":"John","age":25,"gender":"M","upper":"JOHN-25"}""",
      """{"name":"Peter","age":35,"gender":"M","upper":"PETER-35"}""",
      """{"name":"Mary","age":20,"gender":"F","upper":"MARY-20"}"""
    )
  }

  object UpperUdfFunction extends Serializable {
    private def upper(name: String, age: Int): String = s"${name.toUpperCase}-$age"

    def apply(name: Column, age: Column): Column = udf(upper _).apply(name, age)
  }

}
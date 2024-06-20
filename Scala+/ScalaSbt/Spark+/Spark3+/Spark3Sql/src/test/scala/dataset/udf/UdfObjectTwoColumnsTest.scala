package dataset.udf

import factory.Factory
import org.apache.spark.sql.Column
import org.apache.spark.sql.functions._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

/**
 * UDF as a separated object (two columns).
 */
class UdfObjectTwoColumnsTest extends AnyFlatSpec with Matchers {

  it should "extract UDF into separated object (logic is inlined)" in {
    import Factory.ss.implicits._
    val upper: (String, Int) => String = (name: String, age: Int) => s"${name.toUpperCase}-$age"
    val upperUdf = udf(upper: (String, Int) => String)
    val df = Factory.peopleDf.withColumn("upper", upperUdf($"name", $"age"))
    df.toJSON.collect() should contain inOrderOnly(
      """{"name":"John","age":25,"gender":"M","upper":"JOHN-25"}""",
      """{"name":"Peter","age":35,"gender":"M","upper":"PETER-35"}""",
      """{"name":"Mary","age":20,"gender":"F","upper":"MARY-20"}"""
    )
  }

  it should "extract UDF into separated object (logic in a field)" in {
    val df = Factory.peopleDf.withColumn("upper", UpperUdfField(col("name"), col("age")))
    df.toJSON.collect() should contain inOrderOnly(
      """{"name":"John","age":25,"gender":"M","upper":"JOHN-25"}""",
      """{"name":"Peter","age":35,"gender":"M","upper":"PETER-35"}""",
      """{"name":"Mary","age":20,"gender":"F","upper":"MARY-20"}"""
    )
  }

  object UpperUdfField extends Serializable {
    private val upper: (String, Int) => String = (name: String, age: Int) => s"${name.toUpperCase}-$age"

    def apply(nameCol: Column, ageCol: Column): Column = udf(upper).apply(nameCol, ageCol)
  }

  it should "extract UDF into separated object (logic in a function)" in {
    val df = Factory.peopleDf.withColumn("upper", UpperUdfFunction(col("name"), col("age")))
    df.toJSON.collect() should contain inOrderOnly(
      """{"name":"John","age":25,"gender":"M","upper":"JOHN-25"}""",
      """{"name":"Peter","age":35,"gender":"M","upper":"PETER-35"}""",
      """{"name":"Mary","age":20,"gender":"F","upper":"MARY-20"}"""
    )
  }

  object UpperUdfFunction extends Serializable {
    private def upper(name: String, age: Int): String = s"${name.toUpperCase}-$age"

    def apply(nameCol: Column, ageCol: Column): Column = udf(upper _).apply(nameCol, ageCol)
  }

  it should "extract UDF into separated object (logic in apply method)" in {
    val df = Factory.peopleDf.withColumn("upper", UpperUdfApply(col("name"), col("age")))
    df.toJSON.collect() should contain inOrderOnly(
      """{"name":"John","age":25,"gender":"M","upper":"JOHN-25"}""",
      """{"name":"Peter","age":35,"gender":"M","upper":"PETER-35"}""",
      """{"name":"Mary","age":20,"gender":"F","upper":"MARY-20"}"""
    )
  }

  object UpperUdfApply extends Serializable {
    def apply(nameCol: Column, ageCol: Column): Column =
      udf((name: String, age: Int) => s"${name.toUpperCase}-$age").apply(nameCol, ageCol)
  }

}
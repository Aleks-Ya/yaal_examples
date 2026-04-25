package spark4.sql.dataset.udf

import org.apache.spark.sql.Column
import org.apache.spark.sql.functions._
import org.scalatest.flatspec.AnyFlatSpec
import spark4.sql.{Factory, SparkMatchers}

/**
 * UDF as a separated object (single column).
 */
class UdfObjectSingleColumnTest extends AnyFlatSpec with SparkMatchers {

  it should "extract UDF into separated object (logic is inlined)" in {
    val df = Factory.peopleDf.withColumn("upper", UpperUdfInline(col("name"), col("age")))
    df shouldContain(
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
    val df = Factory.peopleDf.withColumn("upper", UpperUdfField(col("name"), col("age")))
    df shouldContain(
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
    val df = Factory.peopleDf.withColumn("upper", UpperUdfFunction(col("name"), col("age")))
    df shouldContain(
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
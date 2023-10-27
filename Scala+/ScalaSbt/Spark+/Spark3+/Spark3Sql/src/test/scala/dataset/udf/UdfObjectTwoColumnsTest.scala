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
    val ds = Factory.peopleDf
    ds.show
    val upper: (String, Int) => String = (name: String, age: Int) => s"${name.toUpperCase}-$age"
    val upperUdf = udf(upper: (String, Int) => String)
    val df = ds.withColumn("upper", upperUdf($"name", $"age"))
    df.toJSON.collect() should contain inOrderOnly(
      """{"name":"John","age":25,"gender":"M","upper":"JOHN-25"}""",
      """{"name":"Peter","age":35,"gender":"M","upper":"PETER-35"}""",
      """{"name":"Mary","age":20,"gender":"F","upper":"MARY-20"}"""
    )
  }

  object UpperUdfInline extends Serializable {
    def apply(col: Column): Column = udf((s: String) => s.toUpperCase).apply(col)
  }

  it should "extract UDF into separated object (logic in a field)" in {
    import Factory.ss.implicits._
    val df = Factory.ss.createDataset(Seq("a", "b"))
      .withColumn("upper", UpperUdfField('value))
    df.toJSON.collect() should contain inOrderOnly(
      """{"value":"a","upper":"A"}""",
      """{"value":"b","upper":"B"}"""
    )
  }

  object UpperUdfField extends Serializable {
    private val upper: String => String = _.toUpperCase

    def apply(col: Column): Column = udf(upper).apply(col)
  }

  it should "extract UDF into separated object (logic in a function)" in {
    import Factory.ss.implicits._
    val df = Factory.ss.createDataset(Seq("a", "b"))
      .withColumn("upper", UpperUdfFunction('value))
    df.toJSON.collect() should contain inOrderOnly(
      """{"value":"a","upper":"A"}""",
      """{"value":"b","upper":"B"}"""
    )
  }

  object UpperUdfFunction extends Serializable {
    private def upper(s: String): String = s.toUpperCase

    def apply(col: Column): Column = udf(upper _).apply(col)
  }

}
package dataset.udf

import factory.Factory
import factory.Factory.ss.implicits._
import org.apache.spark.sql.Column
import org.apache.spark.sql.functions._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

/**
 * UDF returns a JSON object.
 */
class UdfObjectReturnJsonObjectTest extends AnyFlatSpec with Matchers {

  it should "UDF returns JSON object" in {
    val df = Factory.peopleDf.withColumn("person", UpperUdfFunction($"name", $"age"))
    df.toJSON.collect() should contain inOrderOnly(
      """{"name":"John","age":25,"gender":"M","person":{"name":"John","age":25}}""",
      """{"name":"Peter","age":35,"gender":"M","person":{"name":"Peter","age":35}}""",
      """{"name":"Mary","age":20,"gender":"F","person":{"name":"Mary","age":20}}"""
    )
  }

  object UpperUdfFunction extends Serializable {
    private def upper(name: String, age: Int): Person = Person(name, age)

    def apply(name: Column, age: Column): Column = udf(upper _).apply(name, age)
  }


}

case class Person(name: String, age: Int)

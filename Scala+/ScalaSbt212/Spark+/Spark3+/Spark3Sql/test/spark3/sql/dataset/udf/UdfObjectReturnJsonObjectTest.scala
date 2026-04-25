package spark3.sql.dataset.udf

import org.apache.spark.sql.Column
import org.apache.spark.sql.functions._
import org.scalatest.flatspec.AnyFlatSpec
import spark3.sql.{Factory, SparkMatchers}

/**
 * UDF returns a JSON object.
 */
class UdfObjectReturnJsonObjectTest extends AnyFlatSpec with SparkMatchers {

  it should "UDF returns JSON object" in {
    val df = Factory.peopleDf.withColumn("person", UpperUdfFunction(col("name"), col("age")))
    df shouldContain(
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

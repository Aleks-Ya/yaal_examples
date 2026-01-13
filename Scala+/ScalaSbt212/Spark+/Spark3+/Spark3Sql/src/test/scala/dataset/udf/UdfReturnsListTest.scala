package dataset.udf

import factory.Factory
import org.apache.spark.sql.Column
import org.apache.spark.sql.functions._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class UdfReturnsListTest extends AnyFlatSpec with Matchers {

  it should "return a seq" in {
    val df = Factory.peopleDf.withColumn("seq", ToListUdf(col("name"), col("age")))
    df.toJSON.collect should contain inOrderOnly(
      """{"name":"John","age":25,"gender":"M","seq":["JOHN","25"]}""",
      """{"name":"Peter","age":35,"gender":"M","seq":["PETER","35"]}""",
      """{"name":"Mary","age":20,"gender":"F","seq":["MARY","20"]}"""
    )
  }

  object ToListUdf extends Serializable {
    def apply(nameCol: Column, ageCol: Column): Column =
      udf((name: String, age: Int) => Seq(name.toUpperCase, age.toString)).apply(nameCol, ageCol)
  }
}
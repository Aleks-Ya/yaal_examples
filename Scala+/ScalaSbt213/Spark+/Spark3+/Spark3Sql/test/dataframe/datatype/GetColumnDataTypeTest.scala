package dataframe.datatype

import factory.Factory
import org.apache.spark.sql.types.IntegerType
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class GetColumnDataTypeTest extends AnyFlatSpec with Matchers {

  it should "get data type of a column by name" in {
    val df = Factory.peopleDf
    df.schema("age").dataType shouldBe IntegerType
  }

  it should "get data type of a column" in {
    val df = Factory.peopleDf
    val ageCol = df.col("age")
    df.schema(ageCol.toString()).dataType shouldBe IntegerType
  }
}
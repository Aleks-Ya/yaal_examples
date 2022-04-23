package dataframe.compare

import factory.Factory
import org.apache.spark.sql.types.{IntegerType, LongType}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

/**
 * Assert Schema of a DataFrame in unit tests.
 */
class AssertDataFrameSchema extends AnyFlatSpec with Matchers {

  it should "assert a field data type of a DataFrame" in {
    val df = Factory.peopleDf
    df.printSchema()
    df.schema.fields(df.schema.fieldIndex("age")).dataType shouldBe IntegerType
    df.schema.find(_.name.equals("age")).get.dataType shouldBe IntegerType
  }

}
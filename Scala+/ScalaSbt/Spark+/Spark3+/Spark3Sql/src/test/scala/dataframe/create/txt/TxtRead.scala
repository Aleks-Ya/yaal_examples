package dataframe.create.txt

import factory.Factory
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import java.util.Objects.requireNonNull

class TxtRead extends AnyFlatSpec with Matchers {

  it should "read a text file in a DataFrame" in {
    val file = requireNonNull(getClass.getResource("TxtRead.txt"))

    val df1 = Factory.ss.sqlContext.read.textFile(file.getPath)
    val df2 = Factory.ss.sqlContext.read.text(file.getPath)

    df1.toJSON.collect() shouldEqual df2.toJSON.collect()
    df1.schema.simpleString shouldEqual "struct<value:string>"
    df1.toJSON.collect() shouldEqual Array(
      """{"value":"Line 1"}""",
      """{"value":""}""",
      """{"value":"Line 2"}""",
      """{"value":""}""",
      """{"value":"Line 3"}"""
    )
  }

  it should "read a text file with a custom separator" in {
    val file = requireNonNull(getClass.getResource("TxtRead.txt"))

    val df1 = Factory.ss.sqlContext.read.option("lineSep", "\n\n").textFile(file.getPath)
    val df2 = Factory.ss.sqlContext.read.option("lineSep", "\n\n").text(file.getPath)

    df1.toJSON.collect() shouldEqual df2.toJSON.collect()
    df1.schema.simpleString shouldEqual "struct<value:string>"
    df1.toJSON.collect() should contain inOrderOnly(
      """{"value":"Line 1"}""",
      """{"value":"Line 2"}""",
      """{"value":"Line 3"}"""
    )
  }

  it should "read a text file as a single string" in {
    val file = requireNonNull(getClass.getResource("TxtRead.txt"))

    val df1 = Factory.ss.sqlContext.read.option("wholetext", value = true).textFile(file.getPath)
    val df2 = Factory.ss.sqlContext.read.option("wholetext", value = true).text(file.getPath)

    df1.toJSON.collect() shouldEqual df2.toJSON.collect()
    df1.schema.simpleString shouldEqual "struct<value:string>"
    df1.toJSON.collect() should contain only """{"value":"Line 1\n\nLine 2\n\nLine 3"}"""
  }

}
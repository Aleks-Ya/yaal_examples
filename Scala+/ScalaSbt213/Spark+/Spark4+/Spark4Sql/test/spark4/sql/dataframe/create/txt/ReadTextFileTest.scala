package spark4.sql.dataframe.create.txt

import org.apache.spark.sql.DataFrame
import org.scalatest.flatspec.AnyFlatSpec
import spark4.sql.{Factory, SparkMatchers}

import java.net.URL
import java.util.Objects.requireNonNull

class ReadTextFileTest extends AnyFlatSpec with SparkMatchers {

  it should "read a text file in a DataFrame" in {
    val file: URL = requireNonNull(getClass.getResource("ReadTextFileTest.txt"))
    val df: DataFrame = Factory.ss.read.text(file.getPath)
    df shouldHaveDDL "value STRING"
    df shouldContain(
      """{"value":"Line 1"}""",
      """{"value":""}""",
      """{"value":"Line 2"}""",
      """{"value":""}""",
      """{"value":"Line 3"}""")
  }

  it should "read a text file with a custom separator" in {
    val file: URL = requireNonNull(getClass.getResource("ReadTextFileTest.txt"))
    val df: DataFrame = Factory.ss.read.option("lineSep", "\n\n").text(file.getPath)
    df shouldHaveDDL "value STRING"
    df shouldContain(
      """{"value":"Line 1"}""",
      """{"value":"Line 2"}""",
      """{"value":"Line 3"}""")
  }

  it should "read a text file as a single string" in {
    val file: URL = requireNonNull(getClass.getResource("ReadTextFileTest.txt"))
    val df: DataFrame = Factory.ss.read.option("wholetext", value = true).text(file.getPath)
    df shouldHaveDDL "value STRING"
    df shouldContain """{"value":"Line 1\n\nLine 2\n\nLine 3"}"""
  }

}
package spark4.sql.dataset.create.txt

import org.apache.spark.sql.Dataset
import org.scalatest.flatspec.AnyFlatSpec
import spark4.sql.{Factory, SparkMatchers}

import java.net.URL
import java.util.Objects.requireNonNull

class ReadTextFileTest extends AnyFlatSpec with SparkMatchers {

  it should "read a text file in a DataFrame" in {
    val file: URL = requireNonNull(getClass.getResource("ReadTextFileTest.txt"))
    val df: Dataset[String] = Factory.ss.read.textFile(file.getPath)
    df shouldHaveDDL "value STRING"
    df shouldContain(
      "Line 1",
      "",
      "Line 2",
      "",
      "Line 3")
  }

  it should "read a text file with a custom separator" in {
    val file: URL = requireNonNull(getClass.getResource("ReadTextFileTest.txt"))
    val df: Dataset[String] = Factory.ss.read.option("lineSep", "\n\n").textFile(file.getPath)
    df shouldHaveDDL "value STRING"
    df shouldContain(
      "Line 1",
      "Line 2",
      "Line 3")
  }

  it should "read a text file as a single string" in {
    val file = requireNonNull(getClass.getResource("ReadTextFileTest.txt"))
    val df: Dataset[String] = Factory.ss.read.option("wholetext", value = true).textFile(file.getPath)
    df shouldHaveDDL "value STRING"
    df shouldContain
      """Line 1
        |
        |Line 2
        |
        |Line 3""".stripMargin
  }

}
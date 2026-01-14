package dataframe.create.csv

import factory.Factory
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import util.FileUtil

import java.nio.file.Files

class TsvWriteTest extends AnyFlatSpec with Matchers {

  it should "write a TSV-file" in {
    val dir = FileUtil.createAbsentTmpDirPath()

    val expDf = Factory.peopleDf
    expDf.write
      .option("header", "true")
      .option("delimiter", "\t")
      .csv(dir.toString)

    val actDf = Factory.ss.read
      .option("header", "true")
      .option("inferSchema", "true")
      .option("delimiter", "\t")
      .csv(dir.toString)

    expDf.schema shouldEqual actDf.schema
    actDf.toJSON.collect should contain inOrderOnly(
      """{"name":"John","age":25,"gender":"M"}""",
      """{"name":"Peter","age":35,"gender":"M"}""",
      """{"name":"Mary","age":20,"gender":"F"}"""
    )
  }

  it should "write a GZIP-compressed TSV-file" in {
    val dir = FileUtil.createAbsentTmpDirPath()
    println(s"Tmp file name: $dir")

    val expDf = Factory.peopleDf
    expDf.write
      .option("header", "true")
      .option("delimiter", "\t")
      .option("compression", "gzip")
      .csv(dir.toString)

    val actDf = Factory.ss.read
      .option("header", "true")
      .option("inferSchema", "true")
      .option("delimiter", "\t")
      .option("compression", "gzip")
      .csv(dir.toString)

    expDf.schema shouldEqual actDf.schema
    actDf.toJSON.collect should contain inOrderOnly(
      """{"name":"John","age":25,"gender":"M"}""",
      """{"name":"Peter","age":35,"gender":"M"}""",
      """{"name":"Mary","age":20,"gender":"F"}"""
    )
  }
}
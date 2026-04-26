package spark3.sql.dataframe.create.parquet

import org.apache.spark.SparkException
import org.apache.spark.sql.functions.col
import org.apache.spark.sql.{Row, SaveMode}
import org.scalatest.flatspec.AnyFlatSpec
import spark3.sql.{Factory, SparkMatchers}
import util.FileUtil

import java.nio.file.Files

class WriteReadParquetTest extends AnyFlatSpec with SparkMatchers {

  it should "write to parquet file" in {
    val originalDf = Factory.peopleDf

    val file = FileUtil.createAbsentTmpDirStr()
    originalDf.write.parquet(file)

    val parquetDf = Factory.ss.read.parquet(file)
    parquetDf shouldHaveDDL "name STRING,age INT,gender STRING"
    parquetDf shouldContain originalDf
    parquetDf shouldContain(
      """{"name":"John","age":25,"gender":"M"}""",
      """{"name":"Peter","age":35,"gender":"M"}""",
      """{"name":"Mary","age":20,"gender":"F"}""")
  }

  it should "read several parquet files in single DataFrame" in {
    val originalDf = Factory.peopleDf
    val maleDf = originalDf.filter(col("gender") === "M")
    maleDf.count() shouldEqual 2
    val femaleDf = originalDf.filter(col("gender") === "F")
    femaleDf.count() shouldEqual 1

    val maleFile = FileUtil.createAbsentTmpDirStr()
    val femaleFile = FileUtil.createAbsentTmpDirStr()
    maleDf.write.parquet(maleFile)
    femaleDf.write.parquet(femaleFile)

    val parquetDf = Factory.ss.read.parquet(maleFile, femaleFile)
    parquetDf shouldHaveDDL "name STRING,age INT,gender STRING"
    parquetDf shouldContain originalDf
    parquetDf shouldContain(
      """{"name":"John","age":25,"gender":"M"}""",
      """{"name":"Peter","age":35,"gender":"M"}""",
      """{"name":"Mary","age":20,"gender":"F"}""")
  }

  it should "append a parquet file" in {
    val ddl = "city STRING"
    val file = FileUtil.createAbsentTmpDirStr()

    val df1 = Factory.createDf(ddl, Row("London"))
    df1.write.parquet(file)
    Factory.ss.read.parquet(file) shouldContain """{"city":"London"}"""

    val df2 = Factory.createDf(ddl, Row("Berlin"))
    df2.write.mode(SaveMode.Append).parquet(file)
    Factory.ss.read.parquet(file) shouldContain(
      """{"city":"Berlin"}""",
      """{"city":"London"}"""
    )
  }

  it should "read a corrupted Parquet file" in {
    val path = FileUtil.createAbsentTmpDirPath()
    Factory.peopleDf.write.parquet(path.toString)
    Files
      .list(path)
      .filter(_.getFileName.toString.endsWith(".parquet"))
      .forEach(p => Files.write(p, "corrupted".getBytes))
    a[SparkException] should be thrownBy {
      Factory.ss.read.parquet(path.toString)
    }
    a[SparkException] should be thrownBy Factory.ss.read.parquet(path.toString) // one-liner
  }

}
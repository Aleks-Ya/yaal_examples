package spark4.sql.dataframe.create.parquet

import org.apache.spark.SparkException
import org.apache.spark.sql.functions.col
import org.scalatest.flatspec.AnyFlatSpec
import spark4.sql.{Factory, SparkMatchers}
import util.FileUtil

import java.nio.file.Files

class WriteReadPartitionedParquetTest extends AnyFlatSpec with SparkMatchers {

  it should "write to a partitioned parquet file" in {
    val originalDf = Factory.peopleDf
    val path = FileUtil.createAbsentTmpDirPath()
    originalDf.write.partitionBy("gender").parquet(path.toString)
    path.toFile.isDirectory shouldBe true
    path.resolve("gender=F").toFile.isDirectory shouldBe true
    path.resolve("gender=M").toFile.isDirectory shouldBe true

    val parquetDf = Factory.ss.read.parquet(path.toString)
    parquetDf shouldContain originalDf
    parquetDf shouldContain(
      """{"name":"John","age":25,"gender":"M"}""",
      """{"name":"Peter","age":35,"gender":"M"}""",
      """{"name":"Mary","age":20,"gender":"F"}""")
  }

  it should "read a single partition from a partitioned parquet file" in {
    val path = FileUtil.createAbsentTmpDirPath()
    Factory.peopleDf.write.partitionBy("gender").parquet(path.toString)
    val malePartitionPath = path.resolve("gender=M")
    val femalePartitionPath = path.resolve("gender=F")
    path.toFile.isDirectory shouldBe true
    malePartitionPath.toFile.isDirectory shouldBe true
    femalePartitionPath.toFile.isDirectory shouldBe true

    // corrupt the female partition to be sure it will not be read
    Files
      .list(femalePartitionPath)
      .filter(_.getFileName.toString.endsWith(".parquet"))
      .forEach(p => Files.write(p, "corrupted".getBytes))
    a[SparkException] should be thrownBy Factory.ss.read.parquet(path.toString)

    val parquetDf = Factory.ss.read
      .option("basePath", path.toString)
      .parquet(malePartitionPath.toString)
    parquetDf shouldHaveDDL "name STRING,age INT,gender STRING"
    val maleDf = parquetDf.filter(col("gender") === "M")
    maleDf shouldContain(
      """{"name":"John","age":25,"gender":"M"}""",
      """{"name":"Peter","age":35,"gender":"M"}""")
  }

}
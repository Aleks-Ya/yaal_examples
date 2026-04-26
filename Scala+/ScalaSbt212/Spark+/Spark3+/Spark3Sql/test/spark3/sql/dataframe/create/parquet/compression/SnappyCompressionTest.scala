package spark3.sql.dataframe.create.parquet.compression

import org.scalatest.flatspec.AnyFlatSpec
import spark3.sql.{Factory, SparkMatchers}
import util.FileUtil

import java.nio.file.{Files, Path}
import scala.collection.JavaConverters._

class SnappyCompressionTest extends AnyFlatSpec with SparkMatchers {

  it should "write to Snappy parquet file (implicitly)" in {
    val parquetDir = FileUtil.createAbsentTmpDirStr()
    val expDf = Factory.peopleDf
    expDf.write.parquet(parquetDir)
    val actDf = Factory.ss.read.parquet(parquetDir)
    actDf shouldContain expDf
    all(
      Files.list(Path.of(parquetDir)).iterator().asScala
        .map(_.getFileName.toString)
        .filter(_.startsWith("part-"))
        .toSeq
    ) should endWith("-c000.snappy.parquet")
  }

  it should "write to Snappy parquet file (explicitly)" in {
    val parquetDir = FileUtil.createAbsentTmpDirStr()
    val expDf = Factory.peopleDf
    expDf.write.option("compression", "snappy").parquet(parquetDir)
    val actDf = Factory.ss.read.parquet(parquetDir)
    actDf shouldContain expDf
    all(
      Files.list(Path.of(parquetDir)).iterator().asScala
        .map(_.getFileName.toString)
        .filter(_.startsWith("part-"))
        .toSeq
    ) should endWith("-c000.snappy.parquet")
  }
}
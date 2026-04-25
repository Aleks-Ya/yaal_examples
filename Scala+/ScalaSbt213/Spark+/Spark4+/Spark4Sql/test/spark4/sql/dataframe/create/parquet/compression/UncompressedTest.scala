package spark4.sql.dataframe.create.parquet.compression

import org.scalatest.BeforeAndAfterAll
import org.scalatest.flatspec.AnyFlatSpec
import spark4.sql.{Factory, SparkMatchers}
import util.FileUtil

import java.nio.file.{Files, Path}
import scala.collection.JavaConverters._

class UncompressedTest extends AnyFlatSpec with SparkMatchers with BeforeAndAfterAll {
  it should "write to uncompressed parquet file" in {
    val parquetDir = FileUtil.createAbsentTmpDirStr()
    val expDf = Factory.peopleDf
    expDf.write.option("compression", "uncompressed").parquet(parquetDir)
    val actDf = Factory.ss.read.parquet(parquetDir)
    actDf.toJSON.collect shouldEqual expDf.toJSON.collect

    all(
      Files.list(Path.of(parquetDir)).iterator().asScala
        .map(_.getFileName.toString)
        .filter(_.startsWith("part-"))
        .toSeq
    ) should endWith("-c000.parquet")

  }
}
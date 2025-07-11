package dataframe.create.parquet.compression

import factory.Factory
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import util.FileUtil

import java.nio.file.{Files, Path}
import scala.collection.JavaConverters._

class GZipCompressionTest extends AnyFlatSpec with Matchers {
  it should "write to ZIP parquet file" in {
    val parquetDir = FileUtil.createAbsentTmpDirStr()
    val expDf = Factory.peopleDf
    expDf.write.option("compression", "gzip").parquet(parquetDir)
    val actDf = Factory.ss.read.parquet(parquetDir)
    actDf.toJSON.collect shouldEqual expDf.toJSON.collect
    all(
      Files.list(Path.of(parquetDir)).iterator().asScala
        .map(_.getFileName.toString)
        .filter(_.startsWith("part-"))
        .toSeq
    ) should endWith("-c000.gz.parquet")
  }
}
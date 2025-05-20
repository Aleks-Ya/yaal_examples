package dataframe.create.parquet

import factory.Factory
import org.scalatest.BeforeAndAfterAll
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import util.FileUtil

class WriteReadParquetZipTest extends AnyFlatSpec with Matchers with BeforeAndAfterAll {
  it should "write to ZIP parquet file" in {
    val compressionAlgorithm = "gzip"
    val originalDf = Factory.peopleDf

    val gzFile = FileUtil.createAbsentTmpDirStr()
    originalDf.write.option("compression", compressionAlgorithm).parquet(gzFile)

    val sql = Factory.ss.sqlContext
    val parquetDf = sql.read.option("compression", compressionAlgorithm).parquet(gzFile)

    parquetDf.toJSON.collect() shouldEqual originalDf.toJSON.collect()
    parquetDf.toJSON.collect() should contain inOrderOnly(
      """{"name":"John","age":25,"gender":"M"}""",
      """{"name":"Peter","age":35,"gender":"M"}""",
      """{"name":"Mary","age":20,"gender":"F"}""")
  }
}
package dataframe.create.parquet

import factory.Factory
import org.scalatest.BeforeAndAfterAll
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import util.FileUtil

class WriteReadPartitionedParquetTest extends AnyFlatSpec with Matchers with BeforeAndAfterAll {
  it should "write to a partitioned parquet file" in {
    val originalDf = Factory.peopleDf
    val path = FileUtil.createAbsentTmpDirPath()
    originalDf.write.partitionBy("gender").parquet(path.toString)
    path.toFile should be a 'directory
    path.resolve("gender=F").toFile should be a 'directory
    path.resolve("gender=M").toFile should be a 'directory

    val parquetDf = Factory.ss.sqlContext.read.parquet(path.toString)
    parquetDf.toJSON.collect() shouldEqual originalDf.toJSON.collect()
    parquetDf.toJSON.collect() should contain inOrderOnly(
      """{"name":"John","age":25,"gender":"M"}""",
      """{"name":"Peter","age":35,"gender":"M"}""",
      """{"name":"Mary","age":20,"gender":"F"}""")
  }
}
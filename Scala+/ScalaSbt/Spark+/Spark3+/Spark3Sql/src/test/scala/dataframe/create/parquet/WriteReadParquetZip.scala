package dataframe.create.parquet

import factory.Factory
import org.scalatest.BeforeAndAfterAll
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import java.io.File
import java.nio.file.Files

class WriteReadParquetZip extends AnyFlatSpec with Matchers with BeforeAndAfterAll {

  "Parquet test" should "write to ZIP parquet file" in {
    val compressionAlgorithm = "gzip"
    val peopleDf = Factory.peopleDf

    val gzFile = new File(Files.createTempDirectory("parquet").toString, "output.parquet.zip")
    peopleDf.write.option("compression", compressionAlgorithm).parquet(gzFile.toString)

    val sql = Factory.ss.sqlContext
    val parquetDf = sql.read.option("compression", compressionAlgorithm).parquet(gzFile.getAbsolutePath)
    parquetDf.show()
    parquetDf.collect.map(_.toString) should contain inOrderOnly("[Jhon,25]", "[Peter,35]")

    parquetDf.createOrReplaceTempView("parquetPeople")
    val resultDf = sql.sql("SELECT name FROM parquetPeople WHERE age > 30")
    resultDf.show()
    resultDf.collect.map(_.toString) should contain("[Peter]")
  }
}
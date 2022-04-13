package dataframe.create.parquet

import factory.Factory
import org.scalatest.BeforeAndAfterAll
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import java.io.File
import java.nio.file.Files

class WriteReadParquet extends AnyFlatSpec with Matchers with BeforeAndAfterAll {

  "Parquet test" should "write to parquet file" in {
    val peopleDf = Factory.peopleDf

    val file = new File(Files.createTempDirectory("parquet").toString, "output.parquet")
    peopleDf.write.parquet(file.toString)

    val sql = Factory.ss.sqlContext
    val parquetDf = sql.read.parquet(file.getAbsolutePath)
    parquetDf.show()
    parquetDf.collect.map(_.toString) should contain inOrderOnly("[Jhon,25]", "[Peter,35]")

    parquetDf.createOrReplaceTempView("parquetPeople")
    val resultDf = sql.sql("SELECT name FROM parquetPeople WHERE age > 30")
    resultDf.show()
    resultDf.collect.map(_.toString) should contain("[Peter]")
  }
}
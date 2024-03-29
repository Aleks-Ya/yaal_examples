package dataframe.create

import java.io.File
import java.nio.file.Files

import factory.Factory
import org.scalatest.Matchers._
import org.scalatest.{BeforeAndAfterAll, FlatSpec}

class WriteReadParquet extends FlatSpec with BeforeAndAfterAll {

  "Parquet test" should "write to parker file" in {
    val peopleDf = Factory.peopleDf

    val file = new File(Files.createTempDirectory("parquet").toString, "ouput.parquet")
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
package dataframe.create.parquet

import factory.Factory
import factory.Factory.createDf
import org.apache.spark.sql.{DataFrame, Row}
import org.scalatest.BeforeAndAfterAll
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import util.FileUtil

import java.io.File

/**
 * Write/read Parqet in batches.
 */
class BatchesTest extends AnyFlatSpec with Matchers with BeforeAndAfterAll {

  it should "write and read batches" in {
    val outputDir = FileUtil.createAbsentTmpDirStr()

    val size = 100
    val rows = for (i <- 1 to size) yield Row(i)

    val expDf = createDf("id integer", rows: _*)
    val batchNumber = 5
    writeDataFrameBatches(expDf, outputDir, batchNumber)

    val actDf = readDataFrameBatches(outputDir)
    actDf.collect() should contain allElementsOf expDf.collect()
  }

  private def writeDataFrameBatches(df: DataFrame, outputDir: String, batchNumber: Int): Unit = {
    val weights = Array.fill(batchNumber)(1.0 / batchNumber)
    val splitDfs = df.randomSplit(weights)
    splitDfs.zipWithIndex.foreach { case (df, i) =>
      val batchDir = outputDir + s"/batch_$i"
      df.write.parquet(batchDir)
    }
  }

  private def readDataFrameBatches(dir: String) =
    new File(dir)
      .listFiles(_.isDirectory)
      .map(_.getAbsolutePath)
      .map(dir => Factory.ss.read.parquet(dir))
      .reduce(_ union _)
}
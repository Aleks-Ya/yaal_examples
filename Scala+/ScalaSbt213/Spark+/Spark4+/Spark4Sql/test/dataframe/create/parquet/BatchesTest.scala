package dataframe.create.parquet

import factory.Factory
import factory.Factory.createDf
import org.apache.spark.sql.functions.{col, concat, lit}
import org.apache.spark.sql.types.StructType.fromDDL
import org.apache.spark.sql.{DataFrame, Row, SparkSession}
import org.scalatest.BeforeAndAfterAll
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import util.FileUtil

import java.io.File

/**
 * Write/read Parqet in batches.
 */
class BatchesTest extends AnyFlatSpec with Matchers with BeforeAndAfterAll {
  private val IdField = "id"
  private val DataField = "data"
  private val schemaDdl = s"$IdField integer, $DataField string"

  type BatchId = Int
  type BatchNumber = Int

  it should "write and read batches (empty existing DF)" in {
    val outputDir = FileUtil.createExistingTmpDirStr()
    val size = 100
    val maxBatchNumber = 10
    val originDf = createOriginDf(size)
    val batchNumber = calculateBatchNumber(originDf.count, maxBatchNumber)
    processDfInBatches(originDf, outputDir, batchNumber)
    assertOutputDf(outputDir, originDf, size, batchNumber)
  }

  it should "write and read batches (non-empty existing DF)" in {
    val outputDir = FileUtil.createExistingTmpDirStr()
    val maxBatchNumber = 10

    val size1 = 20
    val originDf1 = createOriginDf(size1)
    val batchNumber1 = calculateBatchNumber(originDf1.count, maxBatchNumber)
    processDfInBatches(originDf1, outputDir, batchNumber1)
    assertOutputDf(outputDir, originDf1, size1, batchNumber1)

    val size2 = 100
    val originDf2 = createOriginDf(size2)
    val batchNumber2 = calculateBatchNumber(originDf2.count, maxBatchNumber)
    processDfInBatches(originDf2, outputDir, batchNumber2)
    assertOutputDf(outputDir, originDf2, size2, batchNumber1 + batchNumber2)
  }

  private def processDfInBatches(originDf: DataFrame, outputDir: String, batchNumber: BatchNumber): Unit = {
    val latestBatchId = findLatestBatchId(outputDir)
    val existingDf = readDataFrameBatches(Factory.ss, outputDir)
    val fullDf = existingDf
      .join(originDf, Seq(IdField), "outer")
      .drop(originDf(DataField))
    val missingDf = fullDf.filter(col(DataField).isNull)
    val batches = breakIntoBatches(missingDf, batchNumber)
    batches.zipWithIndex.foreach { case (batchDf, i) =>
      val processedBatchDf = processBatch(batchDf)
      val batchId = latestBatchId.map(_ + 1 + i).getOrElse(i)
      writeBatch(batchId, processedBatchDf, outputDir)
    }
  }

  private def createOriginDf(size: Int): DataFrame = {
    val rows = for (id <- 1 to size) yield Row(id, null)
    createDf(schemaDdl, rows: _*)
  }

  private def calculateBatchNumber(originCount: Long, maxBatchNumber: Int): BatchNumber = {
    val batchSize = 3
    val batchNumber = Math.min(maxBatchNumber, originCount / batchSize).toInt
    println(s"Batch number: $batchNumber")
    batchNumber
  }

  private def breakIntoBatches(df: DataFrame, batchNumber: BatchNumber): Seq[DataFrame] =
    df.randomSplit(Array.fill(batchNumber)(1.0 / batchNumber))

  private def processBatch(batchDf: DataFrame): DataFrame =
    batchDf.withColumn(DataField, concat(lit("data-"), col(IdField)))

  private def writeBatch(batchId: BatchId, batchDf: DataFrame, outputDir: String): Unit =
    batchDf.write.parquet(outputDir + "/" + batchId)

  private def findLatestBatchId(dir: String): Option[BatchId] = {
    val batchDirs = new File(dir).listFiles(_.isDirectory)
    if (batchDirs != null && batchDirs.nonEmpty) Some(batchDirs.map(_.getName.toInt).max) else None
  }

  private def readDataFrameBatches(ss: SparkSession, dir: String) =
    Option(new File(dir).listFiles(_.isDirectory))
      .flatMap(_
        .map(_.getAbsolutePath)
        .map(ss.read.parquet)
        .reduceOption(_ union _)
      ).getOrElse(ss.createDataFrame(new java.util.ArrayList[Row](), fromDDL(s"$IdField integer")))

  private def assertOutputDf(outputDir: String, originDf: DataFrame, expCount: Int, expBatchNumber: Int) = {
    val actDf = readDataFrameBatches(Factory.ss, outputDir)
    val expDf = originDf.select(col("id"), concat(lit("data-"), col("id")))
    actDf.collect() should contain allElementsOf expDf.collect()
    actDf.count() shouldEqual expCount
    val actBatchNumber = Option(new File(outputDir).listFiles(_.isDirectory)).map(_.length).getOrElse(0)
    actBatchNumber shouldEqual expBatchNumber
  }
}
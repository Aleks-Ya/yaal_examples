package spark3.sql.dataset.create.parquet

import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.{Dataset, Encoders}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import spark3.sql.Factory
import util.FileUtil

import java.nio.file.Files
import scala.jdk.StreamConverters.StreamHasToScala

class WriteReadDsToParquetTest extends AnyFlatSpec with Matchers {
  private val ss = Factory.ss

  import ss.implicits._

  it should "write a parquet file (1 partition)" in {
    val expSeq: Seq[Person2] = Seq(Person2("John", 30, gender = true, 0.95D), Person2("Mary", 25, gender = false, 0.90D))
    val expDs: Dataset[Person2] = ss.createDataset(expSeq)
    val parquetFile = FileUtil.createAbsentTmpFile(".parquet")
    expDs.write.parquet(parquetFile.toString)

    val schema: StructType = Encoders.product[Person2].schema
    val actDs: Dataset[Person2] = ss.read.schema(schema).parquet(parquetFile.toString).as[Person2]
    val actSeq = actDs.collect
    actSeq should contain allElementsOf expSeq

    Files.list(parquetFile).filter(_.getFileName.toString.endsWith(".parquet")).count shouldEqual 1
  }

  it should "write a parquet file (2 partitions)" in {
    val expSeq: Seq[Person2] = Seq(Person2("John", 30, gender = true, 0.95D), Person2("Mary", 25, gender = false, 0.90D))

    val partitionsNum = 2
    val expDs: Dataset[Person2] = ss.createDataset(expSeq).repartition(partitionsNum)
    val parquetFile = FileUtil.createAbsentTmpFile(".parquet")
    expDs.write.parquet(parquetFile.toString)

    val schema: StructType = Encoders.product[Person2].schema
    val actDs: Dataset[Person2] = ss.read.schema(schema).parquet(parquetFile.toString).as[Person2]
    val actSeq = actDs.collect
    actSeq should contain allElementsOf expSeq

    Files.list(parquetFile).filter(_.getFileName.toString.endsWith(".parquet")).count shouldEqual partitionsNum
  }

  it should "write a partitioned parquet file" in {
    val expSeq: Seq[Person2] = Seq(Person2("John", 30, gender = true, 0.95D), Person2("Mary", 25, gender = false, 0.90D))

    val expDs: Dataset[Person2] = ss.createDataset(expSeq)
    val parquetFile = FileUtil.createAbsentTmpFile(".parquet")
    expDs.write.partitionBy("gender").parquet(parquetFile.toString)

    val schema: StructType = Encoders.product[Person2].schema
    val actDs: Dataset[Person2] = ss.read.schema(schema).parquet(parquetFile.toString).as[Person2]
    val actSeq = actDs.collect
    actSeq should contain allElementsOf expSeq

    Files.list(parquetFile).map(_.getFileName.toString).toScala(Seq) should contain allOf(
      "gender=true",
      "gender=false",
      "_SUCCESS",
      "._SUCCESS.crc"
    )
  }

}

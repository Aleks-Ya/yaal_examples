package spark4.sql.dataset.create.parquet

import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.{Dataset, Encoders}
import org.scalatest.flatspec.AnyFlatSpec
import spark4.sql.{Factory, SparkMatchers}
import util.FileUtil

class ParquetSchemaEvolutionTest extends AnyFlatSpec with SparkMatchers {

  it should "write a parquet file" in {
    val ss = Factory.ss
    import ss.implicits._

    val expSeq: Seq[Person] = Seq(Person("John", 30, gender = true, 0.95D), Person("Mary", 25, gender = false, 0.90D))
    val expDs: Dataset[Person] = ss.createDataset(expSeq)
    val parquetFile = FileUtil.createAbsentTmpFile(".parquet")
    expDs.write.parquet(parquetFile.toString)

    val schema: StructType = Encoders.product[Person].schema
    val actDs: Dataset[Person] = ss.read.schema(schema).parquet(parquetFile.toString).as[Person]
    val actSeq: Array[Person] = actDs.collect
    actSeq should contain allElementsOf expSeq
  }

}

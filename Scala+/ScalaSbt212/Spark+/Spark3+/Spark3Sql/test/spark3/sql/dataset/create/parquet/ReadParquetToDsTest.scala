package spark3.sql.dataset.create.parquet

import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.{Dataset, Encoders}
import org.scalatest.flatspec.AnyFlatSpec
import spark3.sql.{Factory, SparkMatchers}

import java.io.File

class ReadParquetToDsTest extends AnyFlatSpec with SparkMatchers {

  it should "read a parquet file to a Dataset" in {
    val ss = Factory.ss
    import ss.implicits._

    val file: String = new File(classOf[ReadParquetToDsTest].getResource("sellers_parquet/part-00000-54f48a9e-d67c-4c29-aed7-1b5f6f117c12-c000.snappy.parquet").getFile).getParent
    val schema: StructType = Encoders.product[Seller].schema
    val ds: Dataset[Seller] = ss.read.schema(schema).parquet(file).as[Seller]
    ds.show(false)

    val strings = ds.collect.map(seller => new String(seller.seller_id) + "-" + new String(seller.seller_name) + "-" + new String(seller.daily_target))
    strings should contain allOf("0-seller_0-2500000", "1-seller_1-257237")
  }

}

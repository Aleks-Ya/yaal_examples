package spark4.sql.dataset.create.parquet

import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.{Dataset, Encoders}
import org.scalatest.flatspec.AnyFlatSpec
import spark4.sql.{Factory, SparkMatchers}

import java.io.File

class ReadParquetToDsTest extends AnyFlatSpec with SparkMatchers {
  private val file: String = new File(this.getClass.getResource("sellers_parquet/part-00000-54f48a9e-d67c-4c29-aed7-1b5f6f117c12-c000.snappy.parquet").getFile).getParent
  private val ss = Factory.ss

  it should "read a Parquet file to a Dataset (all columns)" in {
    import ss.implicits._
    val ds: Dataset[Seller] = ss.read.parquet(file).as[Seller]
    val sellers: Array[Seller] = ds.collect
    sellers should contain allOf(
      Seller("0", "seller_0", "2500000"),
      Seller("1", "seller_1", "257237")
    )
  }

  it should "read a Parquet file to a Dataset (excess columns)" in {
    import ss.implicits._
    val schema: StructType = Encoders.product[SellerDetailed].schema
    val ds: Dataset[SellerDetailed] = ss.read.schema(schema).parquet(file).as[SellerDetailed]
    val sellers: Array[SellerDetailed] = ds.collect
    sellers should contain allOf(
      SellerDetailed("0", "seller_0", null, "2500000", null),
      SellerDetailed("1", "seller_1", null, "257237", null)
    )
  }

}

package dataset.create.parquet

import dataset.create.parquet.ReadParquetToDs.Seller
import factory.Factory
import org.scalatest.BeforeAndAfterAll
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import java.io.File

class ReadParquetToDs extends AnyFlatSpec with Matchers with BeforeAndAfterAll {

  "Parquet test" should "read a parquet file to a DataSet" in {
    val ss = Factory.ss
    import org.apache.spark.sql._
    import ss.implicits._

    val schema = Encoders.product[Seller].schema
    val parquet = new File(ReadParquetToDs.getClass.getResource("sellers_parquet/part-00000-54f48a9e-d67c-4c29-aed7-1b5f6f117c12-c000.snappy.parquet").getFile).getParent
    val ds = ss.read.schema(schema).parquet(parquet).as[Seller]

    ds.show(false)
    val strings = ds.collect.map(seller => new String(seller.seller_id) + "-" + new String(seller.seller_name) + "-" + new String(seller.daily_target))
    strings should contain allOf ("0-seller_0-2500000", "1-seller_1-257237")
  }

}

object ReadParquetToDs {
  case class Seller(seller_id: Array[Byte], seller_name: Array[Byte], daily_target: Array[Byte])
}
package app.sixexercises

import org.apache.spark.sql.{Dataset, Encoder, Encoders}

import java.time.LocalDate

object DatasetFactory {

  lazy val sellersDs: Dataset[Seller] = {
    implicit val sellerEncoder: Encoder[Seller] = Encoders.product[Seller]
    DataFrameFactory.sellersDf.map(row => Seller(
      row.getString(row.fieldIndex("seller_id")).toLong,
      row.getString(row.fieldIndex("seller_name")),
      row.getString(row.fieldIndex("daily_target")).toLong))
  }

  lazy val productsDs: Dataset[Product] = {
    implicit val productEncoder: Encoder[Product] = Encoders.product[Product]
    DataFrameFactory.productsDf.map(row => Product(
      row.getString(row.fieldIndex("product_id")).toLong,
      row.getString(row.fieldIndex("product_name")),
      BigDecimal(row.getString(row.fieldIndex("price")))))
  }

  lazy val salesDs: Dataset[Sale] = {
    implicit val productEncoder: Encoder[Sale] = Encoders.product[Sale]
    DataFrameFactory.salesDf.map(row => Sale(row.getString(row.fieldIndex("order_id")).toLong,
      row.getString(row.fieldIndex("product_id")).toLong,
      row.getString(row.schema.fieldIndex("seller_id")).toLong,
      row.getString(row.fieldIndex("num_pieces_sold")).toInt,
      null,
      LocalDate.parse(row.getString(row.schema.fieldIndex("date")))))
  }
}

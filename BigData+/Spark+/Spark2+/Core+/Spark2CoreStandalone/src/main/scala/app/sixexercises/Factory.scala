package app.sixexercises

import org.apache.spark.sql.{Dataset, Encoder, Encoders, SparkSession}

import java.time.LocalDate

object Factory {
  lazy val ss: SparkSession = {
    val builder = SparkSession.builder()
      .master("local")
      .config("spark.sql.autoBroadcastJoinThreshold", -1)
      .config("spark.executor.memory", "500mb")
      .appName("SixExercises")

    val logDir = sys.env.get("SPARK_HISTORY_FS_LOG_DIRECTORY")
    if (logDir.isDefined) {
      builder
        .config("spark.eventLog.enabled", "true")
        .config("spark.eventLog.dir", logDir.get)
    }

    builder.getOrCreate()
  }

  lazy val sellersDs: Dataset[Seller] = {
    val sellersParquet = "/datasets/DatasetToCompleteTheSixSparkExercises/sellers_parquet"
    implicit val sellerEncoder: Encoder[Seller] = Encoders.product[Seller]
    ss.read.parquet(sellersParquet)
      .map(row => Seller(
        row.getString(row.fieldIndex("seller_id")).toLong,
        row.getString(row.fieldIndex("seller_name")),
        row.getString(row.fieldIndex("daily_target")).toLong))
  }

  lazy val productsDs: Dataset[Product] = {
    val productsParquet = "/datasets/DatasetToCompleteTheSixSparkExercises/products_parquet"
    implicit val productEncoder: Encoder[Product] = Encoders.product[Product]
    ss.read.parquet(productsParquet)
      .map(row => Product(
        row.getString(row.fieldIndex("product_id")).toLong,
        row.getString(row.fieldIndex("product_name")),
        BigDecimal(row.getString(row.fieldIndex("price")))))
  }

  lazy val salesDs: Dataset[Sale] = {
    val salesParquet = "/datasets/DatasetToCompleteTheSixSparkExercises/sales_parquet"
    implicit val productEncoder: Encoder[Sale] = Encoders.product[Sale]
    ss.read.parquet(salesParquet).select("order_id", "product_id", "seller_id", "num_pieces_sold", "date")
      .map(row => Sale(row.getString(row.fieldIndex("order_id")).toLong,
        row.getString(row.fieldIndex("product_id")).toLong,
        row.getString(row.schema.fieldIndex("seller_id")).toLong,
        row.getString(row.fieldIndex("num_pieces_sold")).toInt,
        null,
        LocalDate.parse(row.getString(row.schema.fieldIndex("date")))))
  }
}

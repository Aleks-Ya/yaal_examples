package app.sixexercises

import org.apache.spark.sql.{DataFrame, SparkSession}

object DataFrameFactory {
  lazy val ss: SparkSession = {
    val builder = SparkSession.builder().appName("SixExercises")

    val logDir = sys.env.get("SPARK_HISTORY_FS_LOG_DIRECTORY")
    if (logDir.isDefined) {
      builder
        .config("spark.eventLog.enabled", "true")
        .config("spark.eventLog.dir", logDir.get)
    }

    builder.getOrCreate()
  }

  lazy val sellersDf: DataFrame = {
    ss.read.parquet("/datasets/DatasetToCompleteTheSixSparkExercises/sellers_parquet")
  }

  lazy val productsDf: DataFrame = {
    ss.read.parquet("/datasets/DatasetToCompleteTheSixSparkExercises/products_parquet")
  }

  lazy val salesDf: DataFrame = {
    ss.read.parquet("/datasets/DatasetToCompleteTheSixSparkExercises/sales_parquet")
      .select("order_id", "product_id", "seller_id", "num_pieces_sold", "date")
  }
}

package factory

import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}
import org.apache.spark.sql.{DataFrame, Dataset, Row, SparkSession}

object Factory {

  lazy val ss: SparkSession = {
    val builder = SparkSession.builder()
      .appName(getClass.getSimpleName)
      .master("local")

    val logDir = sys.env.get("SPARK_HISTORY_FS_LOG_DIRECTORY")
    if (logDir.isDefined) {
      builder
        .config("spark.eventLog.enabled", "true")
        .config("spark.eventLog.dir", logDir.get)
    }

    builder.getOrCreate()
  }

  lazy val peopleDf: DataFrame = {
    val schema = StructType(
      StructField("name", StringType, nullable = true) ::
        StructField("age", IntegerType, nullable = true) :: Nil)
    val peopleRdd = ss.sparkContext.parallelize(Seq("Jhon,25", "Peter,35"))
    val rowRdd = peopleRdd.map(_.split(",")).map(p => Row(p(0), p(1).toInt))
    val df = ss.sqlContext.createDataFrame(rowRdd, schema)
    df.show
    df.printSchema
    df
  }

  lazy val citiesDf: DataFrame = {
    val schema = StructType(
      StructField("city", StringType, nullable = true) :: Nil)
    val cities = ss.sparkContext.parallelize(Seq("Moscow", "SPb"))
    val rowRdd = cities.map(city => Row(city))
    val df = ss.sqlContext.createDataFrame(rowRdd, schema)
    df.show
    df.printSchema
    df
  }

  lazy val citiesDs: Dataset[String] = {
    val sqlContext = ss.sqlContext
    import sqlContext.implicits._
    val ds = ss.createDataset(Seq("Moscow", "SPb"))
    ds.show
    ds
  }
}
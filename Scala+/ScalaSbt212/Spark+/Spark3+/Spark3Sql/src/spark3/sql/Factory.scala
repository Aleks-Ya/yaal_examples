package spark3.sql

import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD
import org.apache.spark.sql._
import org.apache.spark.sql.types._

import java.util
import scala.collection.JavaConverters._

object Factory {

  lazy val ss: SparkSession = {
    val builder = SparkSession.builder()
      .config("spark.sql.jsonGenerator.ignoreNullFields", "false")
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

  lazy val sc: SparkContext = ss.sparkContext

  lazy val peopleDf: DataFrame = {
    val df: DataFrame = createPeopleDf()
    df.show
    df.printSchema
    df
  }
  lazy val cityListDf: DataFrame = {
    val schema: StructType = StructType.fromDDL("city STRING")
    val rowRdd: RDD[Row] = ss.sparkContext.parallelize(Seq(Row("Moscow"), Row("SPb")))
    val df: DataFrame = ss.createDataFrame(rowRdd, schema)
    df.show
    df.printSchema
    df
  }
  lazy val cityObjectDf: DataFrame = {
    val list: util.List[Row] = Seq(Row("Moscow", 1147), Row("SPb", 1703)).asJava
    val schema: StructType = StructType.fromDDL("name STRING, establishYear INT")
    val df: DataFrame = ss.createDataFrame(list, schema)
    df.show
    df
  }
  lazy val cityDs: Dataset[City] = createCityDs(Seq(City("Moscow", 1147), City("SPb", 1703), City("New York", 1665)))

  def createPeopleDf(): DataFrame = {
    val schema: StructType = StructType.fromDDL("name STRING, age INT, gender STRING")
    createDf(schema, Row("John", 25, "M"), Row("Peter", 35, "M"), Row("Mary", 20, "F"))
  }

  def createCityDs(cities: Seq[City]): Dataset[City] = {
    implicit val encoder: Encoder[City] = Encoders.product[City]
    ss.createDataset(cities)
  }

  def createDf(ddl: String, rows: Row*): DataFrame = {
    val schema = StructType.fromDDL(ddl)
    val df: DataFrame = ss.createDataFrame(ss.sparkContext.parallelize(rows), schema)
    df.show
    df.printSchema
    df
  }

  def createDf(schema: StructType, rows: Row*): DataFrame = {
    val df: DataFrame = ss.createDataFrame(ss.sparkContext.parallelize(rows), schema)
    df.show
    df.printSchema
    df
  }

  def createDf(fields: Map[String, DataType], rows: Row*): DataFrame = {
    val schema: StructType = StructType(fields.map { case (name, dataType) => StructField(name, dataType) }.toList)
    createDf(schema, rows: _*)
  }

}

case class City(name: String, establishYear: Int)
package factory

import org.apache.spark.SparkContext
import org.apache.spark.sql._
import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}

import scala.collection.JavaConverters._

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

  lazy val sc: SparkContext = ss.sparkContext

  lazy val peopleDf: DataFrame = {
    val df = createPeopleDf()
    df.show
    df.printSchema
    df
  }
  lazy val cityListDf: DataFrame = {
    val schema = StructType(
      StructField("city", StringType, nullable = true) :: Nil)
    val cities = ss.sparkContext.parallelize(Seq("Moscow", "SPb"))
    val rowRdd = cities.map(city => Row(city))
    val df = ss.sqlContext.createDataFrame(rowRdd, schema)
    df.show
    df.printSchema
    df
  }
  lazy val cityObjectDf: DataFrame = {
    val list = Seq(City("Moscow", 1147), City("SPb", 1703)).asJava
    val df = ss.createDataFrame(list, classOf[City])
    df.show()
    df
  }
  lazy val cityDs: Dataset[City] = {
    createCityDs(Seq(City("Moscow", 1147), City("SPb", 1703)))
  }

  def createPeopleDf(): DataFrame = {
    val schema = StructType(
      StructField("name", StringType, nullable = true) ::
        StructField("age", IntegerType, nullable = true) ::
        StructField("gender", StringType, nullable = true) :: Nil)
    val peopleRdd = ss.sparkContext.parallelize(Seq("John,25,M", "Peter,35,M", "Mary,20,F"))
    val rowRdd = peopleRdd.map(_.split(",")).map(p => Row(p(0), p(1).toInt, p(2)))
    ss.sqlContext.createDataFrame(rowRdd, schema)
  }

  def createCityDs(cities: Seq[City]): Dataset[City] = {
    implicit val mapEncoder: Encoder[City] = Encoders.product[City]
    ss.createDataset(cities)
  }
}

case class City(name: String, establishYear: Int)
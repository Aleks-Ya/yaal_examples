package dataframe

import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}
import org.apache.spark.sql.{DataFrame, Row, SparkSession}

object DfFactory {

  val ss: SparkSession = SparkSession.builder()
    .appName(getClass.getSimpleName)
    .master("local")
    .getOrCreate()

  val peopleDf: DataFrame = {
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

  val citiesDf: DataFrame = {
    val schema = StructType(
      StructField("city", StringType, nullable = true) :: Nil)
    val cities = ss.sparkContext.parallelize(Seq("Moscow", "SPb"))
    val rowRdd = cities.map(city => Row(city))
    val df = ss.sqlContext.createDataFrame(rowRdd, schema)
    df.show
    df.printSchema
    df
  }
}
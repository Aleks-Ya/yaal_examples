package dataframe

import org.apache.spark.SparkContext
import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}
import org.apache.spark.sql.{Column, Row, SQLContext, SparkSession}
import org.scalatest.{BeforeAndAfterAll, FlatSpec, Matchers}
import org.apache.spark.sql.functions._

class AddColumnTest extends FlatSpec with BeforeAndAfterAll with Matchers {

  var ss: SparkSession = _
  var sc: SparkContext = _
  var sql: SQLContext = _

  override def beforeAll() {
    ss = SparkSession.builder().master("local").getOrCreate()
    sc = ss.sparkContext
    sql = ss.sqlContext
  }

  "Apply index column to DF" should "works" in {
    val newDf = createDf
      .withColumn("text", lit("aaa"))
      .withColumn("number", lit(777))
    newDf.show
    newDf.printSchema
  }

  private def createDf = {
    val schema = StructType(
      StructField("name", StringType, nullable = true) ::
        StructField("age", IntegerType, nullable = true) :: Nil)
    val peopleRdd = sc.parallelize(Seq("Jhon,25", "Peter,35"))
    val rowRdd = peopleRdd.map(_.split(",")).map(p => Row(p(0), p(1).toInt))
    sql.createDataFrame(rowRdd, schema)
  }

  override def afterAll() {
    ss.stop()
  }
}
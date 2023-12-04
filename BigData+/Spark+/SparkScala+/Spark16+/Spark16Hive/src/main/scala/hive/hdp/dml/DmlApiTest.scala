package hive.hdp.dml

import java.util.UUID

import org.apache.spark.sql.hive.HiveContext
import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}
import org.apache.spark.sql.{Row, SaveMode}
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.JavaConverters._

/**
  * Demonstrate DML operations with Java API.
  * Run: spark-submit --class hive.hdp.DmlApiTest spark16_hive.jar
  */
object DmlApiTest {

  def main(args: Array[String]): Unit = {
    val hc = initContext
    val schema = createSchema

    println("Tables: " + hc.tableNames().toList)
    val uuid = UUID.randomUUID().toString
    val data = List(Row(uuid, "Petr", 20)).asJava
    val newDf = hc.createDataFrame(data, schema)
    newDf.show
    val table = "dml_api_test"
    newDf.write.mode(SaveMode.Append).saveAsTable(table)
    println("Tables: " + hc.tableNames().toList)
    hc.table(table).show
  }

  private def initContext = {
    val conf = new SparkConf()
      .setAppName(getClass.getSimpleName)
      .setMaster("local")
    val sc = new SparkContext(conf)
    new HiveContext(sc)
  }

  private def createSchema = {
    val uuidField = StructField("uuid", StringType, nullable = true)
    val nameField = StructField("name", StringType, nullable = true)
    val ageField = StructField("age", IntegerType, nullable = true)
    StructType(uuidField :: nameField :: ageField :: Nil)
  }
}
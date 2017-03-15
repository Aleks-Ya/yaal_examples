package hive.hdp

import org.apache.spark.sql.hive.HiveContext
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Demonstrate DDL operations.
  * Run: spark-submit --class hive.hdp.DDLTest spark16_hive.jar
  */
object DDLTest {

  var hc: HiveContext = _

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
      .setAppName(getClass.getSimpleName)
      .setMaster("local")
    val sc = new SparkContext(conf)
    hc = new HiveContext(sc)
    val hiveImplVersion = classOf[HiveContext].getPackage.getImplementationVersion
    val hiveSpecVersion = classOf[HiveContext].getPackage.getSpecificationVersion
    println(s"Hive version: $hiveSpecVersion, $hiveImplVersion")
    val table = "ddl_table_test"
    println("Table name: " + table)
    executeSQL(s"DROP TABLE IF EXISTS $table")
    executeSQL(s"CREATE TABLE IF NOT EXISTS $table (name STRING, age INT)")
    executeSQL(s"TRUNCATE TABLE $table")
  }

  private def executeSQL(query: String) = {
    println(s"Query: '$query'")
    hc.sql(query)
  }
}
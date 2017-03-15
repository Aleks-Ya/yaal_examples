package hive.hdp

import org.apache.spark.sql.hive.HiveContext
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Demonstrate DML operations with SQL strings.
  * Run: spark-submit --class hive.hdp.DMLTest spark16_hive.jar
  */
object DMLTest {

  var hc: HiveContext = _

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
      .setAppName(getClass.getSimpleName)
      .setMaster("local")
    val sc = new SparkContext(conf)
    hc = new HiveContext(sc)
    val table = "create_table_test"
    println("Table name: " + table)
    executeSQL(s"DROP TABLE IF EXISTS $table")
    executeSQL(s"CREATE TABLE IF NOT EXISTS $table (name STRING, age INT)")
    executeSQL(s"TRUNCATE TABLE $table")
    //    executeSQL(s"INSERT INTO TABLE $table VALUES ('John', 30)")
  }

  private def executeSQL(query: String) = {
    println(s"Query: '$query'")
    hc.sql(query)
  }
}
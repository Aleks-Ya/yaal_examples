package hive.hdp

import org.apache.spark.sql.hive.HiveContext
import org.apache.spark.{SparkConf, SparkContext}

import scala.util.Random

/**
  * Run: spark-submit --class hive.hdp.CreateTableTest spark2_hive.jar
  */
object CreateTableTest {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
      .setAppName(getClass.getSimpleName)
      .setMaster("local")
    val sc = new SparkContext(conf)
    val hc = new HiveContext(sc)
    val hiveImplVersion = classOf[HiveContext].getPackage.getImplementationVersion
    val hiveSpecVersion = classOf[HiveContext].getPackage.getSpecificationVersion
    println(s"Hive version: $hiveSpecVersion, $hiveImplVersion")
    val table = "table_" + Random.nextInt(100)
    println("Table name: " + table)
    hc.sql(s"CREATE TABLE $table (name String, age Integer)")
  }

}
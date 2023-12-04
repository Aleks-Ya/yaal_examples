package hive

import org.apache.hadoop.hive.ql.metadata.HiveUtils

/**
  * Run: spark-submit --class hive.PrintHiveJarVersion spark2_hive.jar
  */
object PrintHiveJarVersion {

  def main(args: Array[String]): Unit = {
    val hiveImplVersion = classOf[HiveUtils].getPackage.getImplementationVersion
    val hiveSpecVersion = classOf[HiveUtils].getPackage.getSpecificationVersion
    println(s"Hive Specification Version: $hiveSpecVersion")
    println(s"Hive Implementation Version: $hiveImplVersion")
  }
}
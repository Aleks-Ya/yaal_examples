package databricks.secrets

import com.databricks.dbutils_v1.DBUtilsHolder.dbutils
import org.apache.spark.SparkContext

object ReadSecretApp {
  def main(args: Array[String]): Unit = {
    println(s"Main class name: ${getClass.getName}")
    println(s"Main method args: ${args.mkString(",")}")
    val sc = SparkContext.getOrCreate()
    val scope = "iablokov-tmp"
    val key = "db-pass"
    val value = dbutils.secrets.get(scope = scope, key = key)
    println(s"Secret in driver: scope=$scope, key=$key, value=${value.mkString(" ")}")
    val words = args.toSeq
    val length = sc.parallelize(words).map(ReadSecretLamda.stringToLength).sum().toInt
    println("Spark calculated length: " + length)
    val expLength = words.map(word => word.length).sum
    println("Expected length: " + expLength)
    assert(length == expLength)
    println("Finished main")
  }
}

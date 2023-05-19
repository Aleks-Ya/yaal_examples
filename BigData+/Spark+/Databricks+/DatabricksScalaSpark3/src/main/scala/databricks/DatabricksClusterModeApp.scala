package databricks

import org.apache.spark.SparkContext

object DatabricksClusterModeApp {
  def main(args: Array[String]): Unit = {
    println(s"Main method args: ${args.mkString(",")}")
    val sc = SparkContext.getOrCreate()
    sc.setLogLevel("DEBUG")
    val words = args.toSeq
    println(s"Words: $words")
    val length = sc.parallelize(words).map(LengthLamda.stringToLength).sum().toInt
    println("Spark calculated length: " + length)
    sc.stop()
    val expLength = words.map(word => word.length).sum
    println("Expected length: " + expLength)
    assert(length == expLength)
  }
}

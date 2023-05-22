package databricks.cluster.shorttrem

import org.apache.spark.SparkContext

object ShortTermDatabricksClusterModeApp {
  def main(args: Array[String]): Unit = {
    println(s"Main class name: ${getClass.getName}")
    println(s"Main method args: ${args.mkString(",")}")
    val sc = SparkContext.getOrCreate()
    val words = args.toSeq
    val length = sc.parallelize(words).map(ShortTermLengthLamda.stringToLength).sum().toInt
    println("Spark calculated length: " + length)
    val expLength = words.map(word => word.length).sum
    println("Expected length: " + expLength)
    assert(length == expLength)
    println("Finished main")
  }
}

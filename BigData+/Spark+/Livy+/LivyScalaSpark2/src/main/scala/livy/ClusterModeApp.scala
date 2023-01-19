package livy

import org.apache.spark.{SparkConf, SparkContext}

object ClusterModeApp {
  def main(args: Array[String]): Unit = {
    println(s"Main method args: ${args.mkString(",")}")
    val conf = new SparkConf()
      .setAppName(getClass.getSimpleName)
      .setMaster("spark://spark-standalone-cluster-master:7077")
    val sc = new SparkContext(conf)
    sc.setLogLevel("DEBUG")
    val words = args.toSeq
    println(s"Words: $words")
    val action = new StringLengthAction(sc)
    val length = action.calcLength(words)
    println("Spark calculated length: " + length)
    sc.stop()
    val expLength = words.map(word => word.length).sum
    println("Expected length: " + expLength)
    assert(length == expLength)
  }
}

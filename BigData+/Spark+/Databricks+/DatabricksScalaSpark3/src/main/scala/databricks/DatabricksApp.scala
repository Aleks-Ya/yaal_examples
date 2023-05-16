package databricks

import org.apache.spark.SparkContext

object DatabricksApp {
  def main(args: Array[String]): Unit = {
    println(s"Main method args: ${args.mkString(",")}")
    val sc = SparkContext.getOrCreate()
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

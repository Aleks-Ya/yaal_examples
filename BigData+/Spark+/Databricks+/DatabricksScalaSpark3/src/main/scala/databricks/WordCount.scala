package databricks

import org.apache.spark.SparkContext

object WordCount {
  def countWords(words: Array[String]): Unit = {
    val length = SparkContext.getOrCreate().parallelize(words).map(JobParamsLamda.stringToLength).sum().toInt
    println("Spark calculated length: " + length)
    val expLength = words.map(word => word.length).sum
    println("Expected length: " + expLength)
    assert(length == expLength)
  }
}

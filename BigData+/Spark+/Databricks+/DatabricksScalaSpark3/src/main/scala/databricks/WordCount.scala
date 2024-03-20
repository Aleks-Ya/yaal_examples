package databricks

import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD

object WordCount {
  def countWords(words: Array[String]): Unit = {
    countWords(SparkContext.getOrCreate().parallelize(words))
  }

  def countWords(wordsRdd: RDD[String]): Unit = {
    val length = wordsRdd.map(JobParamsLamda.stringToLength).sum().toInt
    println("Spark calculated length: " + length)
    val expLength = wordsRdd.map(word => word.length).sum
    println("Expected length: " + expLength)
    assert(length == expLength)
  }
}

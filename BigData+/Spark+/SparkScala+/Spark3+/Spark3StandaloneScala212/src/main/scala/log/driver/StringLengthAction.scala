package log.driver

import org.apache.spark.SparkContext

class StringLengthAction(sc: SparkContext) {
  def calcLength(words: Seq[String]): Int = sc.parallelize(words).map(_.length).sum().toInt
}

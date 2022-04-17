package core

import org.apache.spark.SparkContext

class ConcatStringTransformation(sc: SparkContext) {
  def concatenate(words: Seq[String]): String = sc.parallelize(words).reduce(_ + _)
}

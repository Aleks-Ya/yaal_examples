package spark4.core

import org.apache.spark.rdd.RDD

object Utils {

  def partitionedRddToString(rdd: RDD[(Int, String)]): String = {
    rdd.mapPartitionsWithIndex((partitionIndex, tuples) =>
      tuples.map(tuple => s"$partitionIndex-${tuple._1}-${tuple._2}")
    ).reduce((str1, str2) => s"$str1, $str2")
  }

}

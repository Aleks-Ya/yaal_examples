package artist

import org.apache.spark.{SparkConf, SparkContext}

object ReadArtistsFromGz {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName(getClass.getSimpleName)
    val sc = new SparkContext(conf)
    val rdd = sc.textFile("hdfs://master-service:8020/discogs/artists_sample.xml.gz")
    println("RDD: " + rdd.toDebugString)
    sc.stop()
  }

}
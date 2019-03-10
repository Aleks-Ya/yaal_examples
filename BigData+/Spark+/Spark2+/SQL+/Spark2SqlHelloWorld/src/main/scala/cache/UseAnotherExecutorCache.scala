package cache

import org.apache.spark.storage.StorageLevel
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Submit with build:    ./submit_to_epc.sh cache.UseAnotherExecutorCache
  * Submit without build: ./submit_to_epc.sh cache.UseAnotherExecutorCache --no-build
  */
object UseAnotherExecutorCache {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName(getClass.getSimpleName)
    val sc = new SparkContext(conf)

    val rddCached = sc.parallelize(Seq(1, 2, 3), numSlices = 2)
      .map(number => {
        println(s"====== Process number $number ======")
        number * 2
      })
      .persist(StorageLevel.MEMORY_ONLY_SER_2)

    assert(rddCached.collect().length == 3)
    assert(rddCached.sum == 12)
  }
}

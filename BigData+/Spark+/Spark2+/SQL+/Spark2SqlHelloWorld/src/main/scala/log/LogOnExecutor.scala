package log

import org.apache.log4j.LogManager
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Submit with build:    ./submit_to_epc.sh log.LogOnExecutor
  * Submit without build: ./submit_to_epc.sh log.LogOnExecutor --no-build
  */
object LogOnExecutor {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
      .setAppName(getClass.getSimpleName)
    val sc = new SparkContext(conf)

    class Mapper {
      private lazy val log = LogManager.getLogger(this.getClass)

      def process(rdd: RDD[String]): RDD[String] = {
        log.error(s"Log from Mapper !!!!!!!!!!!!!!!!!!!!!!")
        rdd.map(_.toUpperCase())
      }
    }

    val words = Seq("Hello, ", "World", "!")
    val rdd1 = sc.parallelize(words)
    val mapper = new Mapper
    val rdd2 = mapper.process(rdd1)
    val greeting = rdd2.reduce(_ + _)
    assert("HELLO, WORLD!".equals(greeting))
  }

}

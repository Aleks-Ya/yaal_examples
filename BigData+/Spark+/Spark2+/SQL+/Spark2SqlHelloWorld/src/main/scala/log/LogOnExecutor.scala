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

    class Mapper extends Serializable {
      @transient private lazy val log = LogManager.getLogger(this.getClass)

      def process(rdd: RDD[String]): RDD[String] = {
        rdd.map(word => {
          log.error(s"Log from Mapper !!!!!!!!!!!!!!!!!!!!!!")
          word.toUpperCase()
        })
      }
    }

    val rdd1 = sc.parallelize(Seq("Hello, ", "World", "!"))
    val mapper = new Mapper
    val rdd2 = mapper.process(rdd1)
    val greeting = rdd2.reduce(_ + _)
    assert("HELLO, WORLD!".equals(greeting))
  }

}

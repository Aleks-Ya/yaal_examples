package log.executor

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object LogOnExecutor {

  def main(args: Array[String]): Unit = {
    println("Start")
    val jars = Seq("target/scala-2.11/spark2corestandalone_2.11-1.jar")
    val conf = new SparkConf()
      .setAppName(getClass.getSimpleName)
      .setMaster("spark://spark-standalone-cluster-master:7077")
      .setJars(jars)

    class WordProcessor extends Serializable {
      @transient private lazy val log1 = org.apache.log4j.LogManager.getLogger(this.getClass)

      def processRdd(rdd: RDD[String]): RDD[String] = {
        log1.error("Log1 from Mapper!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!")
        println("Print from Mapper!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!")
        println(s"Root logger level: ${org.apache.log4j.LogManager.getRootLogger.getLevel}")

        throw new RuntimeException("stop")

        rdd.map(word => word.toUpperCase)
      }
    }

    val sc = new SparkContext(conf)
    val rdd = sc.parallelize(Seq("Hello, ", "World", "!"))
    val wordProcessor = new WordProcessor
    val rdd2 = wordProcessor.processRdd(rdd)

    val greeting = rdd2.reduce(_ + _)

    assert("HELLO, WORLD!".equals(greeting))
    println("Greeting: " + greeting)
    println("Finish")
    sc.stop()
  }

}

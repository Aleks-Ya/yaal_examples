package log.executor


import org.apache.log4j.LogManager
import org.apache.spark.{SparkConf, SparkContext}

import java.lang.management.ManagementFactory

object LogLocalModeApp {
  @transient private lazy val log = LogManager.getLogger(this.getClass)

  def main(args: Array[String]): Unit = {
    println("[println] Start: " + ManagementFactory.getRuntimeMXBean.getName)
    log.info("[Log4J] Start: " + ManagementFactory.getRuntimeMXBean.getName)

    val conf = new SparkConf()
      .setAppName(getClass.getSimpleName)
      .setMaster("local[2]")
    val sc = new SparkContext(conf)
    val rdd = sc.parallelize(Seq("Hello, ", "World", "!"))
    val wordProcessor = new WordProcessor
    val rdd2 = wordProcessor.processRdd(rdd)
    val upperCase = rdd2.reduce(_ + _)
    assert("HELLO, WORLD!".equals(upperCase))

    println("[println] Finish: " + ManagementFactory.getRuntimeMXBean.getName)
    log.info("[Log4J] Finish: " + ManagementFactory.getRuntimeMXBean.getName)
    sc.stop()
  }

}

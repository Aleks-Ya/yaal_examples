package log.executor

import org.apache.spark.{SparkConf, SparkContext}

import java.lang.management.ManagementFactory

object DriverLogic {
  @transient private lazy val log4j1 = org.apache.log4j.LogManager.getLogger(getClass)
  @transient private lazy val log4j2 = org.apache.logging.log4j.LogManager.getLogger(getClass)

  def doWork(conf: SparkConf): Unit = {
    printf("[printf][%s][%s] Logic\n", ManagementFactory.getRuntimeMXBean.getName, Thread.currentThread().getName)
    log4j1.info(s"[Log4J1][${ManagementFactory.getRuntimeMXBean.getName}][${Thread.currentThread().getName}] Logic")
    log4j2.info(s"[Log4J2][${ManagementFactory.getRuntimeMXBean.getName}][${Thread.currentThread().getName}] Logic")

    val sc = new SparkContext(conf)
    val words = (1 to 100000).map(n => s"Word $n")
    val rdd = sc.parallelize(words, 3)
    val rdd2 = rdd.map(UpperCaseLambda.toUppercase)
    val actSet = rdd2.collect.toSeq
    sc.stop()
    assert(actSet.size.equals(words.size))
  }
}

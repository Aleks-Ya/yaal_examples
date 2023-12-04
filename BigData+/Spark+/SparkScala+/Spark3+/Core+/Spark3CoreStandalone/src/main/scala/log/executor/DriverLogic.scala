package log.executor

import org.apache.spark.{SparkConf, SparkContext}

import java.lang.Thread.currentThread
import java.lang.management.ManagementFactory.getRuntimeMXBean

object DriverLogic extends LogLevelSystemProperty {
  @transient private lazy val log4j1 = org.apache.log4j.LogManager.getLogger(getClass)
  @transient private lazy val log4j2 = org.apache.logging.log4j.LogManager.getLogger(getClass)

  def doWork(conf: SparkConf): Unit = {
    configureLoggerLevels()
    printf("[printf][%s][%s] Driver Logic Print\n", getRuntimeMXBean.getName, currentThread().getName)

    log4j1.info(s"[Log4J1][${getRuntimeMXBean.getName}][${currentThread().getName}] Driver Logic Info")
    log4j1.debug(s"[Log4J1][${getRuntimeMXBean.getName}][${currentThread().getName}] Driver Logic Debug")

    log4j2.info(s"[Log4J2][${getRuntimeMXBean.getName}][${currentThread().getName}] Driver Logic Info")
    log4j2.debug(s"[Log4J2][${getRuntimeMXBean.getName}][${currentThread().getName}] Driver Logic Debug")

    val sc = new SparkContext(conf)
    val words = (1 to 10).map(n => s"Word $n")
    val rdd = sc.parallelize(words, 3)
    val rdd2 = rdd.map(UpperCaseLambda.toUppercase)
    val actSet = rdd2.collect.toSeq
    sc.stop()
    assert(actSet.size.equals(words.size))
  }
}

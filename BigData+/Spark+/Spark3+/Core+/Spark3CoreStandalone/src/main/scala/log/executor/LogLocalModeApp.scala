package log.executor


import org.apache.spark.SparkConf

import java.lang.management.ManagementFactory

object LogLocalModeApp {
  @transient private lazy val log4j1 = org.apache.log4j.LogManager.getLogger(getClass)
  @transient private lazy val log4j2 = org.apache.logging.log4j.LogManager.getLogger(getClass)

  def main(args: Array[String]): Unit = {
    printf("[printf][%s][%s] Start\n", ManagementFactory.getRuntimeMXBean.getName, Thread.currentThread().getName)
    log4j1.error(s"[Log4J1][${ManagementFactory.getRuntimeMXBean.getName}][${Thread.currentThread().getName}] Start")
    log4j2.error(s"[Log4J2][${ManagementFactory.getRuntimeMXBean.getName}][${Thread.currentThread().getName}] Start")

    val conf = new SparkConf()
      .setAppName(getClass.getSimpleName)
      .setMaster("local[2]")

    DriverLogic.doWork(conf)

    printf("[printf][%s][%s] Finish\n", ManagementFactory.getRuntimeMXBean.getName, Thread.currentThread().getName)
    log4j1.info(s"[Log4J1][${ManagementFactory.getRuntimeMXBean.getName}][${Thread.currentThread().getName}] Finish")
    log4j2.info(s"[Log4J2][${ManagementFactory.getRuntimeMXBean.getName}][${Thread.currentThread().getName}] Finish")
  }

}

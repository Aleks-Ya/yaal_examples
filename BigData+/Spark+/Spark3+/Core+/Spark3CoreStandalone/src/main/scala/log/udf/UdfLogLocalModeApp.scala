package log.udf

import org.apache.spark.sql.SparkSession

import java.lang.Thread.currentThread
import java.lang.management.ManagementFactory.getRuntimeMXBean

object UdfLogLocalModeApp {
  @transient private lazy val log4j1 = org.apache.log4j.LogManager.getLogger(getClass)
  @transient private lazy val log4j2 = org.apache.logging.log4j.LogManager.getLogger(getClass)

  def main(args: Array[String]): Unit = {
    printf("[printf][%s][%s] Start\n", getRuntimeMXBean.getName, currentThread().getName)
    log4j1.error(s"[Log4J1][${getRuntimeMXBean.getName}][${currentThread().getName}] Start")
    log4j2.error(s"[Log4J2][${getRuntimeMXBean.getName}][${currentThread().getName}] Start")

    val ss = SparkSession.builder()
      .appName(getClass.getSimpleName)
      .master("local[2]")
      .getOrCreate()

    UdfDriverLogic.doWork(ss)

    ss.stop()

    printf("[printf][%s][%s] Finish\n", getRuntimeMXBean.getName, currentThread().getName)
    log4j1.info(s"[Log4J1][${getRuntimeMXBean.getName}][${currentThread().getName}] Finish")
    log4j2.info(s"[Log4J2][${getRuntimeMXBean.getName}][${currentThread().getName}] Finish")
  }

}

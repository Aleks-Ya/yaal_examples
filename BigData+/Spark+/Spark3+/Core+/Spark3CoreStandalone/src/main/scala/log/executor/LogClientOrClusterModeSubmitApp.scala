package log.executor

import org.apache.log4j.LogManager
import org.apache.spark.SparkConf

import java.lang.management.ManagementFactory

object LogClientOrClusterModeSubmitApp {
  @transient private lazy val log = LogManager.getLogger(this.getClass)

  def main(args: Array[String]): Unit = {
    printf("[printf][%s][%s] Start\n", ManagementFactory.getRuntimeMXBean.getName, Thread.currentThread().getName)
    log.error(s"[Log4J][${ManagementFactory.getRuntimeMXBean.getName}][${Thread.currentThread().getName}] Start")

    val conf = new SparkConf().setAppName(getClass.getSimpleName)

    Logic.doWork(conf)

    printf("[printf][%s][%s] Finish\n", ManagementFactory.getRuntimeMXBean.getName, Thread.currentThread().getName)
    log.info(s"[Log4J][${ManagementFactory.getRuntimeMXBean.getName}][${Thread.currentThread().getName}] Finish")
  }

}

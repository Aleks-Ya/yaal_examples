package log.executor

import org.apache.spark.SparkConf

import java.lang.Thread.currentThread
import java.lang.management.ManagementFactory.getRuntimeMXBean

object LogClientModeIdeApp {
  @transient private lazy val log4j1 = org.apache.log4j.LogManager.getLogger(getClass)
  @transient private lazy val log4j2 = org.apache.logging.log4j.LogManager.getLogger(getClass)

  def main(args: Array[String]): Unit = {
    printf("[printf][%s][%s] Start\n", getRuntimeMXBean.getName, currentThread().getName)
    log4j1.error(s"[Log4J1][${getRuntimeMXBean.getName}][${currentThread().getName}] Start")
    log4j2.error(s"[Log4J2][${getRuntimeMXBean.getName}][${currentThread().getName}] Start")

    val jars = Seq("target/scala-2.12/spark3corestandalone_2.12-1.jar")
    val conf = new SparkConf()
      .setAppName(getClass.getSimpleName)
      .setMaster("spark://spark-standalone-cluster-master:7077")
      .set("spark.executor.cores", "1")
      .set("spark.executor.memory", "512M")
      .set("spark.deploy.defaultCores", "1")
      .set("spark.cores.max", "2")
      .set("spark.eventLog.enabled", "true")
      .set("spark.eventLog.dir", "file:///media/aleks/ADATA/dataset/spark-events")
      .setJars(jars)

    DriverLogic.doWork(conf)

    printf("[printf][%s][%s] Finish\n", getRuntimeMXBean.getName, currentThread().getName)
    log4j1.info(s"[Log4J1][${getRuntimeMXBean.getName}][${currentThread().getName}] Finish")
    log4j2.info(s"[Log4J2][${getRuntimeMXBean.getName}][${currentThread().getName}] Finish")
  }

}

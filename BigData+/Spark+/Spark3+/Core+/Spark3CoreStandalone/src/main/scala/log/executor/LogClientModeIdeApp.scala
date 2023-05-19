package log.executor

import org.apache.log4j.LogManager
import org.apache.spark.SparkConf

import java.lang.management.ManagementFactory

object LogClientModeIdeApp {
  @transient private lazy val log = LogManager.getLogger(this.getClass)

  def main(args: Array[String]): Unit = {
    printf("[printf][%s][%s] Start\n", ManagementFactory.getRuntimeMXBean.getName, Thread.currentThread().getName)
    log.error(s"[Log4J][${ManagementFactory.getRuntimeMXBean.getName}][${Thread.currentThread().getName}] Start")

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

    Logic.doWork(conf)

    printf("[printf][%s][%s] Finish\n", ManagementFactory.getRuntimeMXBean.getName, Thread.currentThread().getName)
    log.info(s"[Log4J][${ManagementFactory.getRuntimeMXBean.getName}][${Thread.currentThread().getName}] Finish")
  }

}

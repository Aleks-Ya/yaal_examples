package shared_variables.accumulator.long_accumulator

import org.apache.spark.SparkConf

import java.lang.Thread.currentThread
import java.lang.management.ManagementFactory.getRuntimeMXBean

object LongAccumulatorClientModeIdeApp {
  def main(args: Array[String]): Unit = {
    printf("[%s][%s] Start\n", getRuntimeMXBean.getName, currentThread().getName)

    val jars = Seq("target/scala-2.12/spark3corestandalone_2.12-1.jar")
    val conf = new SparkConf()
      .setAppName(getClass.getSimpleName)
      .setMaster("spark://spark-standalone-cluster-master:7077")
      .set("spark.executor.cores", "1")
      .set("spark.executor.memory", "512M")
      .set("spark.deploy.defaultCores", "1")
      .set("spark.cores.max", "2")
      .set("spark.eventLog.enabled", "true")
      .set("spark.eventLog.dir", "file:/tmp/spark-standalone-cluster-shared/spark-events")
      .setJars(jars)

    DriverLogic.doWork(conf)

    printf("[%s][%s] Finish\n", getRuntimeMXBean.getName, currentThread().getName)
  }

}

package shared_variables.accumulator.long_accumulator

import org.apache.spark.SparkConf

import java.lang.Thread.currentThread
import java.lang.management.ManagementFactory.getRuntimeMXBean

object LongAccumulatorClientOrClusterModeSubmitApp {
  def main(args: Array[String]): Unit = {
    printf("[%s][%s] Start\n", getRuntimeMXBean.getName, currentThread().getName)
    val conf = new SparkConf().setAppName(getClass.getSimpleName)

    DriverLogic.doWork(conf)

    printf("[%s][%s] Finish\n", getRuntimeMXBean.getName, currentThread().getName)
  }

}

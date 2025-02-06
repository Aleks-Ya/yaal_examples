package shared_variables.broadcast.accumulator_in_broadcast_variable

import org.apache.spark.SparkConf

import java.lang.Thread.currentThread
import java.lang.management.ManagementFactory.getRuntimeMXBean

object AccumulatorInBroadcastVariableLocalModeApp {
  def main(args: Array[String]): Unit = {
    printf("[%s][%s] Start\n", getRuntimeMXBean.getName, currentThread().getName)
    val conf = new SparkConf()
      .setAppName(getClass.getSimpleName)
      .setMaster("local[2]")

    DriverLogic.doWork(conf)

    printf("[%s][%s] Finish\n", getRuntimeMXBean.getName, currentThread().getName)
  }

}

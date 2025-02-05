package shared_variables.accumulator.long_accumulator

import org.apache.spark.{SparkConf, SparkContext}

import java.lang.Thread.currentThread
import java.lang.management.ManagementFactory.getRuntimeMXBean

object DriverLogic {
  def doWork(conf: SparkConf): Unit = {
    printf("[%s][%s] Driver Logic Print\n", getRuntimeMXBean.getName, currentThread().getName)

    val sc = new SparkContext(conf)
    val counter = sc.longAccumulator("counter")

    val words = (1 to 10).map(n => s"Word $n")
    val rdd = sc.parallelize(words, 3)
    val rdd2 = rdd.map(UpperCaseLambda.toUppercase(_, counter))
    val actSet = rdd2.collect.toSeq
    println(s"Counter: ${counter.value}")
    sc.stop()
    assert(actSet.size.equals(words.size))
    assert(counter.value == words.size)
  }
}

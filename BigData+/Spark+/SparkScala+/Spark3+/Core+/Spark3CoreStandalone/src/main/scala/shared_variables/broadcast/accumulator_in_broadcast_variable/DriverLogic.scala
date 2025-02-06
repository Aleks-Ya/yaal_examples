package shared_variables.broadcast.accumulator_in_broadcast_variable

import org.apache.spark.{SparkConf, SparkContext}

import java.lang.Thread.currentThread
import java.lang.management.ManagementFactory.getRuntimeMXBean

object DriverLogic {
  def doWork(conf: SparkConf): Unit = {
    printf("[%s][%s] Driver Logic Print\n", getRuntimeMXBean.getName, currentThread().getName)

    val sc = new SparkContext(conf)
    val counter = sc.longAccumulator("counter")
    val replaceDictionaryMap = Map("Word 2" -> "Collocation 2", "Word 4" -> "Collocation 4")
    val data = new Data(replaceDictionaryMap)
    val replaceDictionary = sc.broadcast(data)

    val words = (1 to 5).map(n => s"Word $n")
    val rdd = sc.parallelize(words, 3)
    val rdd2 = rdd.map(UpperCaseLambda.toUppercase(_, replaceDictionary, counter))
    val actSet = rdd2.collect.toSeq
    sc.stop()
    assert(actSet.equals(Seq("WORD 1", "COLLOCATION 2", "WORD 3", "COLLOCATION 4", "WORD 5")))
    val counterValueAct = counter.value
    val counterValueExp = words.size
    assert(counterValueAct == counterValueExp, s"Counter: actual=$counterValueAct , expected=$counterValueExp")
  }
}

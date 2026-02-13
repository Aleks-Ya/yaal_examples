package shared_variables.accumulator.long_accumulator

import org.apache.spark.util.LongAccumulator

import java.lang.Thread.currentThread
import java.lang.management.ManagementFactory.getRuntimeMXBean

object UpperCaseLambda {
  def toUppercase(word: String, counter: LongAccumulator): String = {
    printf("[%s][%s] Executor UpperCaseLambda Print '%s' \n", getRuntimeMXBean.getName, currentThread().getName, word)
    counter.add(1)
    word.toUpperCase
  }
}

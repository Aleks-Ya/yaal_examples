package shared_variables.broadcast.broadcast_variable

import org.apache.spark.broadcast.Broadcast

import java.lang.Thread.currentThread
import java.lang.management.ManagementFactory.getRuntimeMXBean

object UpperCaseLambda {
  def toUppercase(word: String, replaceDictionary: Broadcast[Map[String, String]]): String = {
    val dictionary = replaceDictionary.value
    val dictionaryHash = System.identityHashCode(dictionary)
    printf("[%s][%s] Executor UpperCaseLambda: word='%s', dictionaryHash='%s' \n",
      getRuntimeMXBean.getName, currentThread().getName, word, dictionaryHash)
    if (dictionary.contains(word)) replaceDictionary.value(word).toUpperCase else word.toUpperCase
  }
}

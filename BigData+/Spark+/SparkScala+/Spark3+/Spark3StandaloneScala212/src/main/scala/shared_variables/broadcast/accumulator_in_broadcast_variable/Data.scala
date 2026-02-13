package shared_variables.broadcast.accumulator_in_broadcast_variable

import java.lang.Thread.currentThread
import java.lang.management.ManagementFactory.getRuntimeMXBean

class Data(replaceDictionary: Map[String, String]) extends Serializable {

  def replace(word: String): String = {
    val dictionary = replaceDictionary
    val dictionaryHash = System.identityHashCode(dictionary)
    printf("[%s][%s] Executor Data: word='%s', dictionaryHash='%s' \n",
      getRuntimeMXBean.getName, currentThread().getName, word, dictionaryHash)
    if (dictionary.contains(word)) replaceDictionary(word).toUpperCase else word.toUpperCase
  }

}

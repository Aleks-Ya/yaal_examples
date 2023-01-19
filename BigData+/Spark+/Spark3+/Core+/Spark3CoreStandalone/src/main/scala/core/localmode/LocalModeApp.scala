package core.localmode

import core.StringLengthAction
import org.apache.spark.{SparkConf, SparkContext}

object LocalModeApp {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
      .setAppName(getClass.getSimpleName)
      .setMaster("local[2]")
    val sc = new SparkContext(conf)
    val words = Seq("Hello, ", "World", "!")
    val action = new StringLengthAction(sc)
    val length = action.calcLength(words)
    sc.stop()
    assert(length == 13)
    println("Finish")
  }

}

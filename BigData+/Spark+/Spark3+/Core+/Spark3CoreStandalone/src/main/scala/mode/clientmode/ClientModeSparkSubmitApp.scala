package mode.clientmode

import mode.StringLengthAction
import org.apache.spark.{SparkConf, SparkContext}

object ClientModeSparkSubmitApp {

  def main(args: Array[String]): Unit = {
    println("Start")
    val conf = new SparkConf().setAppName(getClass.getSimpleName)
    val sc = new SparkContext(conf)
    val words = Seq("Hello, ", "World", "!")
    val action = new StringLengthAction(sc)
    val length = action.calcLength(words)
    println("Length: " + length)
    sc.stop()
    assert(length == 13)
    println("Finish")
  }

}

package mode.clustermode

import mode.StringLengthAction
import org.apache.spark.{SparkConf, SparkContext}

object ClusterModeApp {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName(getClass.getSimpleName)
    val sc = new SparkContext(conf)
    sc.setLogLevel("DEBUG")
    val words = Seq("Hello, ", "World", "!")
    val action = new StringLengthAction(sc)
    val length = action.calcLength(words)
    println("Length: " + length)
    sc.stop()
    assert(length == 13)
  }
}

package log.driver

import org.apache.spark.{SparkConf, SparkContext}

object DriverLogLevelLocalModeApp {
  private val DRIVER_LOG_LEVEL: String = "DEBUG"

  def main(args: Array[String]): Unit = {
    println("Start")
    val jars = Seq("target/scala-2.12/spark2corestandalone_2.12-1.jar")
    val conf = new SparkConf()
      .setAppName(getClass.getSimpleName)
      .setMaster("spark://spark-standalone-cluster-master:7077")
      .set("spark.executor.cores", "1")
      .set("spark.executor.memory", "512M")
      .set("spark.deploy.defaultCores", "1")
      .set("spark.cores.max", "2")
      .setJars(jars)
    val sc = new SparkContext(conf)
    sc.setLogLevel(DRIVER_LOG_LEVEL)
    val words = Seq("Hello, ", "World", "!")
    val action = new StringLengthAction(sc)
    val length = action.calcLength(words)
    println("Length: " + length)
    sc.stop()
    assert(length == 13)
    println("Finish")
  }

}

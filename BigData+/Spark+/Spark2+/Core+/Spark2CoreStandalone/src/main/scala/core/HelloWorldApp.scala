package core

import org.apache.spark.{SparkConf, SparkContext}

object HelloWorldApp {

  def main(args: Array[String]): Unit = {
    println("Start")
    val jars = Seq("target/scala-2.12/spark2corestandalone_2.12-1.jar")
    val masterIp = "spark-standalone-cluster-master"
    val conf = new SparkConf()
      .setAppName(getClass.getSimpleName)
      .setMaster(s"spark://$masterIp:7077")
      .set("spark.executor.cores", "1")
      .set("spark.executor.memory", "512M")
      .set("spark.deploy.defaultCores", "1")
      .set("spark.cores.max", "2")
      .setJars(jars)
    val sc = new SparkContext(conf)
    val words = Seq("Hello, ", "World", "!")
    val transformation = new ConcatStringTransformation(sc)
    val greeting = transformation.concatenate(words)
    sc.stop()
    assert("Hello, World!".equals(greeting))
    println("Greeting: " + greeting)
    println("Finish")
  }

}
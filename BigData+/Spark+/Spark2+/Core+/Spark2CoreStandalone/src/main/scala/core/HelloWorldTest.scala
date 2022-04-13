/**
  * Run Spark cluster from yaal_examples/Building+/Docker+/DockerImage+/Application+/Spark
  * Find master IP address: docker inspect --format='{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' spark_master_1
  * Build: sbt package
  * Run core.HelloWorldTest#main()
  */
package core

import org.apache.spark.{SparkConf, SparkContext}

object HelloWorldTest {

  def main(args: Array[String]): Unit = {
    println("Start")
    val jars = Seq("target/scala-2.11/spark2corestandalone_2.11-1.jar")
    val masterIp = "spark-standalone-cluster-master"
    val conf = new SparkConf()
      .setAppName(getClass.getSimpleName)
      .setMaster(s"spark://$masterIp:7077")
      .set("spark.executor.cores", "1")
      .set("spark.executor.memory", "512M")
      .set("spark.deploy.defaultCores", "1")
      .setJars(jars)
    val sc = new SparkContext(conf)
    val words = Seq("Hello, ", "World", "!")
    val greeting = sc.parallelize(words).reduce(_ + _)
    assert("Hello, World!".equals(greeting))
    println("Greeting: " + greeting)
    println("Finish")
        sc.stop()
  }

}
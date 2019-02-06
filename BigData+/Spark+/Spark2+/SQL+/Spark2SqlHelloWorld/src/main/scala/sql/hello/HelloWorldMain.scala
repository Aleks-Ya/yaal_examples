package sql.hello

import org.apache.spark.{SparkConf, SparkContext}

object HelloWorldMain {

  def main(args: Array[String]): Unit = {
    println(s"Environment variables:\n${System.getenv}")
    println(s"System properties:\n${System.getProperties}")

    println("Starting...")
    val conf = new SparkConf()
      .setAppName(getClass.getSimpleName)
    val sc = new SparkContext(conf)
    val words = Seq("Hello, ", "World", "!")
    val greeting = sc.parallelize(words).reduce(_ + _)
    assert("Hello, World!".equals(greeting))
    println("Greeting: " + greeting)
    println("Finished.")
  }

}

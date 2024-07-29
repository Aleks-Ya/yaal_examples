package sql.hello

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Submit with build:    ./submit_to_epc.sh sql.hello.HelloWorldMain
  * Submit without build: ./submit_to_epc.sh sql.hello.HelloWorldMain --no-build
  */
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

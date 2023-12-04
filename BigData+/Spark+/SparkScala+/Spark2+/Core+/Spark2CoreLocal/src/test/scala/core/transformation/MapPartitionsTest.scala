package core.transformation

import core.Factory
import org.scalatest.{FlatSpec, Matchers}

class MapPartitionsTest extends FlatSpec with Matchers {


  it should "use mapPartitions to initialize executor" in {
    def initExecutor(): Unit = {
      println("Executor is initialized")
    }

    def cleanupExecutor(): Unit = {
      println("Executor is cleaned up")
    }

    val rdd = Factory.sc.parallelize(Array(1, 2, 3), 2)
    rdd.mapPartitions(partition => {
      initExecutor()
      println("Processing partition elements...")
      partition.foreach(element => println(element))
      println("Finished partition elements")
      val list = List("a", "b")
      cleanupExecutor()
      list.iterator
    })
      .foreach(element => {
        println(element)
      })
  }


}

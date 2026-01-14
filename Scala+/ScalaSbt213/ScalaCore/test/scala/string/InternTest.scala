package scala.string

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import java.lang.management.ManagementFactory
import scala.collection.mutable.ListBuffer

class InternTest extends AnyFlatSpec with Matchers {
  private val listNumber = 10
  private val stringNumberInList = 1000000

  it should "generate big list without interning" in {
    val startTime = System.currentTimeMillis()
    val lists = ListBuffer[List[String]]()
    for (i <- 1 to listNumber) {
      println(s"Processing list $i")
      val list = generateStringList(stringNumberInList)
      assert(list.length == stringNumberInList + 1)
      lists += list
    }
    val endTime = System.currentTimeMillis()
    println()
    println("WITHOUT INTERNING")
    printf("Duration: %,d ms%n", endTime - startTime)
    printMemory()
    assert(lists.length == listNumber)
  }

  it should "generate big list with interning" in {
    val startTime = System.currentTimeMillis()
    val lists = ListBuffer[List[String]]()
    for (i <- 1 to listNumber) {
      println(s"Processing list $i")
      val list = generateStringList(stringNumberInList).map(_.intern)
      assert(list.length == stringNumberInList + 1)
      lists += list
    }
    val endTime = System.currentTimeMillis()
    println()
    println("WITH INTERNING")
    printf("Duration: %,d ms%n", endTime - startTime)
    printMemory()
    assert(lists.length == listNumber)
  }

  private def generateStringList(number: Int) =
    (10000000 to 10000000 + number).toList.map(_.toString)

  private def printMemory(): Unit = {
    Runtime.getRuntime.gc()
    val total = Runtime.getRuntime.totalMemory
    val free = Runtime.getRuntime.freeMemory
    printf("List number: %,d%n", listNumber)
    printf("String number in list: %,d%n", stringNumberInList)
    printf("Total: %,d%n", Long.box(total))
    printf("Free: %,d%n", Long.box(free))
    printf("Busy: %,d%n", Long.box(total - free))
    printf("Max: %,d%n", Long.box(Runtime.getRuntime.maxMemory))
    printf("Used memory: %,d%n", ManagementFactory.getMemoryMXBean.getHeapMemoryUsage.getUsed)
  }

}

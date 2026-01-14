package scala.collection.java

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import scala.collection.JavaConverters._

class JavaCollectionToScalaTest extends AnyFlatSpec with Matchers {

  it should "convert a Java list to a Scala list" in {
    val javaList = java.util.Arrays.asList(1, 2)
    val scalaList = javaList.asScala
    scalaList should contain inOrder(1, 2)
  }

  it should "instantiate a Java list" in {
    val javaList1 = java.util.Arrays.asList(1, 2)
    val javaList2 = new java.util.ArrayList()
    javaList1 should contain inOrder(1, 2)
    javaList2 should be(empty)
  }

}

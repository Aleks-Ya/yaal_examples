package collection.java

import org.scalatest.{FlatSpec, Matchers}

import scala.collection.JavaConverters._

class JavaCollectionToScalaTest extends FlatSpec with Matchers {

  it should "convert a Java list to a Scala list" in {
    val javaList = java.util.Arrays.asList(1, 2)
    val scalaList = javaList.asScala
    scalaList should contain inOrder(1, 2)
  }

}

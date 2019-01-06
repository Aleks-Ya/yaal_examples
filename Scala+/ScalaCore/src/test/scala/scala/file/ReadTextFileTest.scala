package scala.file

import org.scalatest.{FlatSpec, Matchers}

import scala.io.Source

class ReadTextFileTest extends FlatSpec with Matchers {

  it should "read whole file to string" in {
    val uri = getClass.getResource("readme.txt").toURI
    val content = Source.fromFile(uri).getLines.mkString("\n")
    println(content)
    content shouldEqual "hi\nbye"
  }

}

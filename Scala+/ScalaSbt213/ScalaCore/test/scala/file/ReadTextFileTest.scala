package scala.file

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import java.io.{BufferedReader, FileReader}
import scala.collection.mutable.ListBuffer
import scala.io.Source

class ReadTextFileTest extends AnyFlatSpec with Matchers {

  it should "use Source#fromFile" in {
    val uri = getClass.getResource("readme.txt").toURI
    val source = Source.fromFile(uri)
    val content = source.getLines.mkString("\n")
    source.close()
    content shouldEqual "hi\nbye"
  }

  it should "read while readLine not null" in {
    val file = getClass.getResource("readme.txt").getFile
    val br = new BufferedReader(new FileReader(file))
    val lines: ListBuffer[String] = ListBuffer()
    var line: String = null
    while ( {
      line = br.readLine
      line != null
    }) {
      lines += line
    }
    br.close()
    val content = lines.mkString("\n")
    content shouldEqual "hi\nbye"
  }

  it should "use Source#fromResource" in {
    val source = Source.fromResource("scala/file/readme.txt")
    val content = source.getLines.mkString("\n")
    source.close()
    content shouldEqual "hi\nbye"
  }
}

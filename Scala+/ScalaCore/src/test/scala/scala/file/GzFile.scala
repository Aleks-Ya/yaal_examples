package scala.file

import java.io.{BufferedInputStream, FileInputStream}
import java.util.zip.GZIPInputStream

import org.scalatest.{FlatSpec, Matchers}

import scala.io.Source

class GzFile extends FlatSpec with Matchers {

  it should "read content of GZ file" in {
    val gzPath = getClass.getResource("gz_read.txt.gz").getFile
    val is = new GZIPInputStream(new BufferedInputStream(new FileInputStream(gzPath)))
    val source = Source.fromInputStream(is)
    val content = source.mkString
    is.close()
    content shouldEqual "hello, GZ!"
  }

}

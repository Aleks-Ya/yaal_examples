package scala.clazz

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import java.io.{ByteArrayInputStream, InputStream}

class CastClass extends AnyFlatSpec with Matchers {

  it should "cast class" in {
    val is: InputStream = new ByteArrayInputStream(Array[Byte]())
    val bais: ByteArrayInputStream = is.asInstanceOf[ByteArrayInputStream]
    bais should be(is)
  }

}

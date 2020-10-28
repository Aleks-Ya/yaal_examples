package scala.clazz

import java.io.{ByteArrayInputStream, InputStream}

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class CastClass extends AnyFlatSpec with Matchers {

  it should "cast class" in {
    val is: InputStream = new ByteArrayInputStream(Array[Byte]())
    val bais: ByteArrayInputStream = is.asInstanceOf[ByteArrayInputStream]
    bais should be(is)
  }

}

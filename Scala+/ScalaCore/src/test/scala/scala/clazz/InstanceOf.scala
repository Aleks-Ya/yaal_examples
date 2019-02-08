package scala.clazz

import java.io.{ByteArrayInputStream, FileInputStream, InputStream}

import org.scalatest.{FlatSpec, Matchers}

class InstanceOf extends FlatSpec with Matchers {

  it should "check class of an object" in {
    val is: InputStream = new ByteArrayInputStream(Array[Byte]())
    is.isInstanceOf[ByteArrayInputStream] shouldBe true
    is.isInstanceOf[InputStream] shouldBe true
    is.isInstanceOf[FileInputStream] shouldBe false
    null.isInstanceOf[FileInputStream] shouldBe false
  }

  it should "check class of null" in {
    null.isInstanceOf[FileInputStream] shouldBe false
    null.isInstanceOf[Any] shouldBe false
    val nullVal = null
    nullVal.isInstanceOf[Any] shouldBe false
  }

}

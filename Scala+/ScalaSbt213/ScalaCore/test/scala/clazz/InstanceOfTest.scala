package scala.clazz

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import java.io.{ByteArrayInputStream, FileInputStream, InputStream}

class InstanceOfTest extends AnyFlatSpec with Matchers {

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

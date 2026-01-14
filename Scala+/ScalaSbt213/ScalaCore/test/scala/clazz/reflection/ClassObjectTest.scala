package scala.clazz.reflection

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ClassObjectTest extends AnyFlatSpec with Matchers {


  it should "get class with 'classOf'" in {
    val anyClass = classOf[Class[_]]
  }

  it should "get class with 'this'" in {
    class Abc {
      val clazz: Class[_] = this.getClass
    }
    val abc = new Abc
    abc.clazz shouldEqual classOf[Abc]
  }

}

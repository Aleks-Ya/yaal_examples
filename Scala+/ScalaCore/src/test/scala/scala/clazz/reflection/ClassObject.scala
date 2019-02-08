package scala.clazz.reflection

import org.scalatest.{FlatSpec, Matchers}

class ClassObject extends FlatSpec with Matchers {


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

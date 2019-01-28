package scala.clazz.reflection

import org.scalatest.{FlatSpec, Matchers}

class ClassObject extends FlatSpec with Matchers {


  it should "get Class<?> object" in {
    val anyClass = classOf[Class[_]]
  }

}

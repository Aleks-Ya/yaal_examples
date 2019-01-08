package scala.clazz

import org.scalatest.{FlatSpec, Matchers}

class Singleton extends FlatSpec with Matchers {

  it should "read value of a singleton" in {
    object MySingleton {
      val name = "abc"
    }
    val actName = MySingleton.name
    actName shouldEqual "abc"
  }

}

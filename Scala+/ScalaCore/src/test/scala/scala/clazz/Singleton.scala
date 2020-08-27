package scala.clazz

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class Singleton extends AnyFlatSpec with Matchers {

  it should "read value of a singleton" in {
    object MySingleton {
      val name = "abc"
    }
    val actName = MySingleton.name
    actName shouldEqual "abc"
  }

}

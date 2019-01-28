package scala.clazz.implicit_class

import org.scalatest.{FlatSpec, Matchers}

class ImplicitClass extends FlatSpec with Matchers {

  it should "repeat function several times" in {
    object Helpers {
      implicit class IntWithTimes(x: Int) {
        def times[A](f: => A): Unit = {
          def loop(current: Int): Unit =
            if(current > 0) {
              f
              loop(current - 1)
            }
          loop(x)
        }
      }
    }
    import Helpers._
    5 times println("HI")
  }

}

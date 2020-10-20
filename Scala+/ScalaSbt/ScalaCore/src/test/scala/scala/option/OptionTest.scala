package scala.option

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class OptionTest extends AnyFlatSpec with Matchers {

  it should "create Option" in {
    val o1 = Some(1)
    val o2 = None
  }
}
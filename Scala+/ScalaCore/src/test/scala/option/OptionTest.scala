package option

import org.scalatest.{FlatSpec, Matchers}

class OptionTest extends FlatSpec with Matchers {

  it should "create Option" in {
    val o1 = Some(1)
    val o2 = None
  }
}
package scala.variable

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class DeclareVariableTest extends AnyFlatSpec with Matchers {

  it should "declare a constant" in {
    val a = -3
    a shouldEqual -3
  }

  it should "declare before using" in {
    var s = None: Option[String]
    val n = 1
    if (n > 0) {
      s = Some("Positive")
    } else {
      s = Some("Negative")
    }
    s.isDefined shouldBe true
    s.get shouldEqual "Positive"
  }

}

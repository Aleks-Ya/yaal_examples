package scala.flow

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class MatchTest extends AnyFlatSpec with Matchers {

  it should "match" in {
    val code = "M"
    val gender = code match {
      case "M" => "man"
      case "W" => "women"
      case _ => "shit"
    }
    gender shouldEqual "man"
  }

  it should "throw exception from match" in {
    assertThrows[RuntimeException] {
      val code = "M"
      code match {
        case "M" => throw new RuntimeException("man " + code)
        case "W" => throw new IllegalArgumentException("women " + code)
        case _ => throw new NullPointerException("null " + code)
      }
    }
  }

  it should "compare with variables" in {
    val manCode = "M"
    val womenCode = "W"
    val code = "M"
    val gender = code match {
      case `manCode` => "man"
      case `womenCode` => "women"
      case _ => "shit"
    }
    gender shouldEqual "man"
  }


}

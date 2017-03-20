package condition

import org.scalatest.{FlatSpec, Matchers}

class MatchTest extends FlatSpec with Matchers {

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

}

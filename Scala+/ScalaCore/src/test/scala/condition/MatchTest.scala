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

}

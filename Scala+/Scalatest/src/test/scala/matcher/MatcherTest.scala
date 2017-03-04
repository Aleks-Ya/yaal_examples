package matcher

import org.scalatest.FlatSpec
import org.scalatest.Matchers.convertToStringShouldWrapper

class MatcherTest extends FlatSpec {

  "shouldEqual matcher" should "work" in {
    "abc" shouldEqual "abc"
  }

}
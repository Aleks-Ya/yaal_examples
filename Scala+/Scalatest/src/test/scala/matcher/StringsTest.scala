package matcher

import org.scalatest.FlatSpec
import org.scalatest.Matchers.convertToStringShouldWrapper

class StringsTest extends FlatSpec {

  "shouldEqual matcher" should "work" in {
    "abc" shouldEqual "abc"
  }

}
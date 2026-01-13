package scalatest.spec

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class AnyWordSpecTest extends AnyWordSpec with Matchers {
  "A Set" when {
    "empty" should {
      "have size 0" in {
        assert(Set.empty.size === 0)
      }
    }
  }
}
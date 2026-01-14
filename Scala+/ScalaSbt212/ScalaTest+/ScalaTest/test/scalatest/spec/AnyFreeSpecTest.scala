package scalatest.spec

import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers

class AnyFreeSpecTest extends AnyFreeSpec with Matchers {
  "A Set" - {
    "when empty" - {
      "should have size 0" in {
        assert(Set.empty.size === 0)
      }

      "should produce NoSuchElementException when head is invoked" in {
        assertThrows[NoSuchElementException] {
          Set.empty.head
        }
      }
    }
  }
}
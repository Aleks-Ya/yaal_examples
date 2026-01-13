package scalamock.spec

import org.scalamock.scalatest.MockFactory
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class AnyWordSpecTest extends AnyWordSpec with Matchers with MockFactory {

  "A Set" when {
    "empty" should {
      "have size 0" in {
        class Country {
          def getName = "Philippines"
        }
        val countryMock = mock[Country]
        val expName = "Russia"
        (countryMock.getName _).expects().returning(expName)
        countryMock.getName shouldEqual expName
      }
    }
  }

}
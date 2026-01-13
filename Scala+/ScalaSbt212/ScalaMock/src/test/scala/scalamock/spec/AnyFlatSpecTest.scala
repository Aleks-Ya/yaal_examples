package scalamock.spec

import org.scalamock.scalatest.MockFactory
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class AnyFlatSpecTest extends AnyFlatSpec with Matchers with MockFactory {

  it should "mock an object" in {
    class Country {
      def getName = "Philippines"
    }
    val countryMock = mock[Country]
    val expName = "Russia"
    (countryMock.getName _).expects().returning(expName)
    countryMock.getName shouldEqual expName
  }

}
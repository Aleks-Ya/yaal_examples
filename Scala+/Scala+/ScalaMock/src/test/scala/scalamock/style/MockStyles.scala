package scalamock.style

import org.scalamock.scalatest.MockFactory
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

/**
 * Docs: http://scalamock.org/user-guide/mocking_style/
 */
class MockStyles extends AnyFlatSpec with Matchers with MockFactory {

  it should "use Expectation-First style" in {
    class Country {
      def getName = "Philippines"

      def getArea = 300000
    }
    val countryMock = mock[Country]
    val expName = "Russia"
    val expArea = 17000000
    (countryMock.getName _).expects().returning(expName)
    (countryMock.getArea _).expects().returning(expArea)
    countryMock.getName shouldEqual expName
    countryMock.getArea shouldEqual expArea
  }

  it should "use Record-Then-Verify style (Mockito)" in {
    class Country {
      def getName = "Philippines"

      def getArea = 300000
    }
    val countryMock = stub[Country]
    val expName = "Russia"
    val expArea = 17000000
    (countryMock.getName _).when().returns(expName)
    (countryMock.getArea _).when().returns(expArea)
    countryMock.getName shouldEqual expName
    countryMock.getArea shouldEqual expArea
    (countryMock.getName _).verify().once()
  }

}
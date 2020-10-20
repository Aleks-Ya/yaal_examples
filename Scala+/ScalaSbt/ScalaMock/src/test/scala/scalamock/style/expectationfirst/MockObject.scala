package scalamock.style.expectationfirst

import org.scalamock.scalatest.MockFactory
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class MockObject extends AnyFlatSpec with Matchers with MockFactory {

  it should "mock an object" in {
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

}
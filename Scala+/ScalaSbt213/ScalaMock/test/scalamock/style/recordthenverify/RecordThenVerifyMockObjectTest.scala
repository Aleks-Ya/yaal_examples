package scalamock.style.recordthenverify

import org.scalamock.scalatest.MockFactory
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class RecordThenVerifyMockObjectTest extends AnyFlatSpec with Matchers with MockFactory {

  it should "mock an object" in {
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

  it should "mock methods with parameters" in {
    class Country {
      def getName(prefix: String) = s"$prefix Philippines"
    }
    val countryMock = stub[Country]
    val expName = "Russia"
    (countryMock.getName _).when(*).returns(expName)
    countryMock.getName("The") shouldEqual expName
    (countryMock.getName _).verify("The").once()
  }

}
package scalamock.style.expectationfirst

import org.scalamock.scalatest.MockFactory
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class MockObject extends AnyFlatSpec with Matchers with MockFactory {

  it should "mock an object" in {
    class Country {
      def getName = "Philippines"

      def getArea = 300000

      def unitMethodWithoutParams(): Unit = println("Logging")

      def unitMethodWithParams(text: String): Unit = println(s"Info: $text")

      def unitMethodOverloaded(): Unit = println("Logging")

      def unitMethodOverloaded(text: String): Unit = println(s"Logging: $text")

      def unitMethodOverloaded(text: String, num: Int): Unit = println(s"Logging: $text $num")
    }

    val countryMock = mock[Country]
    val expName = "Russia"
    val expArea = 17000000

    (countryMock.getName _).expects().returning(expName)
    (countryMock.getArea _).expects().returning(expArea)
    (countryMock.unitMethodWithoutParams _).expects().returning()
    (countryMock.unitMethodWithParams _).expects(*).returning()
    (countryMock.unitMethodOverloaded: () => Unit).expects().returning()
    (countryMock.unitMethodOverloaded: String => Unit).expects(*).returning()
    (countryMock.unitMethodOverloaded: (String, Int) => Unit).expects(*, *).returning()

    countryMock.getName shouldEqual expName
    countryMock.getArea shouldEqual expArea
    countryMock.unitMethodWithoutParams()
    countryMock.unitMethodWithParams("England")
    countryMock.unitMethodOverloaded()
    countryMock.unitMethodOverloaded("England")
    countryMock.unitMethodOverloaded("England", 100)
  }

}
package scalamock.style.expectationfirst

import org.scalamock.scalatest.MockFactory
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class MockObject extends AnyFlatSpec with Matchers with MockFactory {
  private val countryMock = mock[Country]
  private val expName = "Russia"
  private val expArea = 17000000

  it should "mock an object" in {
    (countryMock.getName _).expects().returning(expName)
    (countryMock.getArea _).expects().returning(expArea)
    (countryMock.unitMethodWithoutParams _).expects().returning()
    (countryMock.unitMethodWithParams _).expects(*).returning()

    countryMock.getName shouldEqual expName
    countryMock.getArea shouldEqual expArea
    countryMock.unitMethodWithoutParams()
    countryMock.unitMethodWithParams("England")
  }

  it should "return nothing" in {
    (countryMock.unitMethodWithoutParams _).expects().returning()
    (countryMock.unitMethodWithParams _).expects(*).returning()

    countryMock.unitMethodWithoutParams()
    countryMock.unitMethodWithParams("England")
  }

  it should "return specific value" in {
    (countryMock.getName _).expects().returning(expName)
    (countryMock.getArea _).expects().returning(expArea)

    countryMock.getName shouldEqual expName
    countryMock.getArea shouldEqual expArea
  }

  it should "return different values" in {
    val value1 = "name1"
    val value2 = "name2"

    (countryMock.getName _).expects().returning(value1)
    (countryMock.getName _).expects().returning(value2)

    countryMock.getName shouldEqual value1
    countryMock.getName shouldEqual value2
  }

  it should "once, twice, never" in {
    (countryMock.getName _).expects().returning(expName).once()
    (countryMock.getArea _).expects().returning(expArea).twice()
    (countryMock.unitMethodWithParams _).expects(*).returning().never()

    countryMock.getName shouldEqual expName
    countryMock.getArea shouldEqual expArea
    countryMock.getArea shouldEqual expArea
  }

  it should "overloaded methods" in {
    (countryMock.unitMethodOverloaded: () => Unit).expects().returning()
    (countryMock.unitMethodOverloaded: String => Unit).expects(*).returning()
    (countryMock.unitMethodOverloaded: (String, Int) => Unit).expects(*, *).returning()

    countryMock.unitMethodOverloaded()
    countryMock.unitMethodOverloaded("England")
    countryMock.unitMethodOverloaded("England", 100)
  }

  it should "expect any value" in {
    (countryMock.unitMethodWithParams _).expects(*).returning()
    countryMock.unitMethodWithParams("England")
  }

  it should "expect specific values" in {
    (countryMock.unitMethodOverloaded: (String, Int) => Unit).expects("England", 100).returning()
    (countryMock.unitMethodOverloaded: (String, Int) => Unit).expects("Germany", 200).returning().never()
    (countryMock.unitMethodOverloaded: (String, Int) => Unit).expects(*, *).returning().never()

    countryMock.unitMethodOverloaded("England", 100)
  }

  class Country {
    def getName = "Philippines"

    def getArea = 300000

    def unitMethodWithoutParams(): Unit = println("Logging")

    def unitMethodWithParams(text: String): Unit = println(s"Info: $text")

    def unitMethodOverloaded(): Unit = println("Logging")

    def unitMethodOverloaded(text: String): Unit = println(s"Logging: $text")

    def unitMethodOverloaded(text: String, num: Int): Unit = println(s"Logging: $text $num")
  }

}
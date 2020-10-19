package mockito

import org.scalamock.scalatest.MockFactory
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class CreateMock extends AnyFlatSpec with Matchers with MockFactory {

  it should "mock" in {
    type PlayerId = Int
    type CountryId = Int
    case class Country(id: CountryId, name: String)
    case class Player(id: PlayerId, nickname: String, country: Country)

    val countryMock = mock[Country]
  }

}
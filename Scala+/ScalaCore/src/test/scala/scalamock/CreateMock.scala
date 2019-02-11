package scalamock

import org.scalamock.scalatest.MockFactory
import org.scalatest.{FlatSpec, Matchers}

class CreateMock extends FlatSpec with Matchers with MockFactory {

  it should "mock" in {
    type PlayerId = Int
    type CountryId = Int
    case class Country(id: CountryId, name: String)
    case class Player(id: PlayerId, nickname: String, country: Country)

    val countryMock = mock[Country]
  }

  it should "create stub" in {
    type PlayerId = Int
    type CountryId = Int
    case class Country(id: CountryId, name: String)
    case class Player(id: PlayerId, nickname: String, country: Country)

    val playerStub = stub[Player]
  }

}
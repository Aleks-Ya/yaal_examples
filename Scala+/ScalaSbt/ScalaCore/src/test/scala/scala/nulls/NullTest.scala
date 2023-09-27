package scala.nulls

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class NullTest extends AnyFlatSpec with Matchers {

  it should "compare with null" in {
    val v: String = null
    val isNull = v == null
    val isNotNull = v != null
    v shouldBe null
    isNull shouldBe true
    isNotNull shouldBe false
  }

  it should "safety read nullable object properties" in {
    val usa = County("USA")
    val newYork = City("New York", usa)
    newYork.country.name shouldBe "USA"

    val london = City("London", null)
    london.country shouldBe null
  }

  case class County(name: String)

  case class City(name: String, country: County)


}
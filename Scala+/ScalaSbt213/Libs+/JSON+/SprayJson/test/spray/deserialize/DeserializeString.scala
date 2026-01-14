package spray.deserialize

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import spray.City
import spray.json._

class DeserializeString extends AnyFlatSpec with Matchers {

  it should "deserialize JSON from String" in {
    import spray.MyJsonProtocol._
    val str = City.moscowJson
    val jsonAst = str.parseJson
    val city = jsonAst.convertTo[City]
    city shouldEqual City.moscowCity
  }

}


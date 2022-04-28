package spray.serialize

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import spray.City
import spray.json._

class SerializeToString extends AnyFlatSpec with Matchers {

  it should "serialize object to json" in {
    import spray.MyJsonProtocol._
    val obj = City.moscowCity
    val jsonAst = obj.toJson
    val str = jsonAst.toString
    str shouldEqual """{"establishYear":1147,"name":"Moscow"}"""
  }

}



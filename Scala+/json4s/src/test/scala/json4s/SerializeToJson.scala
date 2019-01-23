package json4s

import org.json4s.JsonDSL._
import org.json4s.{NoTypeHints, _}
import org.json4s.native.JsonMethods._
import org.json4s.native.Serialization
import org.json4s.native.Serialization.write
import org.scalatest.{FlatSpec, Matchers}

class SerializeToJson extends FlatSpec with Matchers {

  it should "serialize List[Int] to json" in {
    val list = List(1, 2, 3)
    val json = compact(render(list))
    json shouldEqual "[1,2,3]"
  }

  it should "serialize object to json" in {
    implicit val formats: AnyRef with Formats = Serialization.formats(NoTypeHints)
    val city = City("Moscow", 1147)
    val json = write(city)
    json shouldEqual """{"name":"Moscow","establishYear":1147}"""
  }
}

private case class City(name: String, establishYear: Int)

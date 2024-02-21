package json

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import play.api.libs.functional.syntax.toFunctionalBuilderOps
import play.api.libs.json.JsValue.jsValueToJsLookup
import play.api.libs.json._

class DeserializeReads extends AnyFlatSpec with Matchers {

  case class Person1(name: String, age: Int, cities: List[String]) //Can't be in test body

  it should "deserialize JSON to a case class (automated mapping)" in {
    implicit val reads: Reads[Person1] = Json.reads[Person1]
    val json = """{"name":"John","age":30, "cities": ["Moscow", "New York"]}"""
    val jsValue = Json.parse(json)
    val personAct = jsValue.as[Person1]
    val personExp = Person1("John", 30, List("Moscow", "New York"))
    personAct shouldEqual personExp
  }

  it should "deserialize JSON to a case class (manual mapping)" in {
    case class Person(name: String, age: Int, position: Position, oldPositions: List[Position])
    case class Position(id: Long, title: String)

    implicit val positionReads: Reads[Position] = (
      (__ \ "id").read[Long] and
        (__ \ "title").read[String]
      ) (Position.apply _)

    implicit val personReads: Reads[Person] = (
      (__ \ "name").read[String] and
        (__ \ "age").read[Int] and
        (__ \ "position").read[Position] and
        (__ \ "oldPositions").read[List[Position]]
      ) (Person.apply _)

    val jsValue = Json.parse(
      s"""{
         |"name":"John",
         |"age":30,
         |"position": {"id":1, "title":"Boss"},
         |"oldPositions": [ {"id": 2, "title": "Programmer"}, {"id":3, "title": "Tester"}]
         |}""".stripMargin)

    val personExp = Person("John", 30, Position(1, "Boss"), List(Position(2L, "Programmer"), Position(3L, "Tester")))

    //With validation
    val personResult = jsValue.validate[Person]
    val personValidatedAct = personResult.get
    personValidatedAct shouldEqual personExp

    //Without validation
    val personAct = jsValue.as[Person]
    personAct shouldEqual personExp
  }

  it should "deserialize JSON to a case class (manual mapping, extends Reads)" in {
    case class Person(name: String, age: Int, position: Position, oldPositions: List[Position])
    case class Position(id: Long, title: String)

    implicit object positionReads extends Reads[Position] {
      override def reads(json: JsValue): JsResult[Position] = json match {
        case js: JsObject =>
          val id = (js \ "id").as[Long]
          val title = (js \ "title").as[String]
          val position = Position(id, title)
          JsSuccess(position)
        case x => JsError(s"Unexpected json: $x")
      }
    }

    implicit object personReads extends Reads[Person] {
      override def reads(json: JsValue): JsResult[Person] = json match {
        case js: JsObject =>
          val name = (js \ "name").as[String]
          val age = (js \ "age").as[Int]
          val position = (js \ "position").as[Position]
          val oldPositions = (js \ "oldPositions").as[List[Position]]
          val person = Person(name, age, position, oldPositions)
          JsSuccess(person)
        case x => JsError(s"Unexpected json: $x")
      }
    }

    val jsValue = Json.parse(
      s"""{
         |"name":"John",
         |"age":30,
         |"position": {"id":1, "title":"Boss"},
         |"oldPositions": [ {"id": 2, "title": "Programmer"}, {"id":3, "title": "Tester"}]
         |}""".stripMargin)
    val personResult = jsValue.validate[Person]
    val personAct = personResult.get
    val personExp = Person("John", 30, Position(1, "Boss"), List(Position(2L, "Programmer"), Position(3L, "Tester")))
    personAct shouldEqual personExp
  }

}

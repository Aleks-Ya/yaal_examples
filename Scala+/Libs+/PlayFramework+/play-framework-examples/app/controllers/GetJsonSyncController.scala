package controllers

import javax.inject._
import play.api.libs.functional.syntax.toFunctionalBuilderOps
import play.api.libs.json.{Json, Writes, __}
import play.api.mvc._

/**
 * Return JSON for a GET request to http://localhost:9000/sync/get/text
 */
@Singleton
class GetJsonSyncController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {

  def json(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    case class Person(name: String, age: Int)
    val data = Person("John", 30)
    implicit val personWriter: Writes[Person] = (
      (__ \ Symbol("name")).write[String] and (__ \ Symbol("age")).write[Long]
      ) (foo => (foo.name, foo.age))
    val json = Json.toJson(data).toString()
    Ok(json)
  }
}

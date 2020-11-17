package controllers

import javax.inject._
import play.api.libs.json.{Json, OFormat}
import play.api.mvc._

@Singleton
class PlayLiquibasePersonController @Inject()(val controllerComponents: ControllerComponents,
                                              private val personService: PlayLiquibasePersonService) extends BaseController {
  private implicit val personFormat: OFormat[PlayLiquibasePerson] = Json.format[PlayLiquibasePerson]

  def getPersons: Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    val body = Json.toJson(personService.getAll)
    Ok(body)
  }

  def createPerson: Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    val jsonValue = request.body.asJson.get
    val person = jsonValue.as[PlayLiquibasePerson]
    personService.create(person)
    Ok
  }

  def deletePerson(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    val personId = request.path.toInt
    personService.delete(personId)
    Ok
  }
}

package controllers

import javax.inject._
import play.api.libs.json.{Json, OFormat}
import play.api.mvc._

@Singleton
class PlaySlickLiquibasePersonController @Inject()(val controllerComponents: ControllerComponents,
                                                   private val personService: PlaySlickLiquibasePersonService) extends BaseController {
  private implicit val personFormat: OFormat[PlaySlickLiquibasePerson] = Json.format[PlaySlickLiquibasePerson]

  def getPersons: Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    val body = Json.toJson(personService.getAll)
    Ok(body)
  }

  def createPerson: Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    val jsonValue = request.body.asJson.get
    val person = jsonValue.as[PlaySlickLiquibasePerson]
    personService.create(person)
    Ok
  }

  def deletePerson(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    val personId = request.path.toInt
    personService.delete(personId)
    Ok
  }
}

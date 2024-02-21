package controllers

import javax.inject._
import play.api.libs.json.JsValue.jsValueToJsLookup
import play.api.mvc._

@Singleton
class PostController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {

  case class RequestBody(name: String)

  def json(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    val jsonValue = request.body.asJson.get
    val name = (jsonValue \ "name").asOpt[String].get
    val nicknameList = (jsonValue \ "nicknames").asOpt[List[String]].get
    val nicknameStr = nicknameList.mkString(", ")
    Ok(f"Hello, $name ($nicknameStr)!")
  }
}

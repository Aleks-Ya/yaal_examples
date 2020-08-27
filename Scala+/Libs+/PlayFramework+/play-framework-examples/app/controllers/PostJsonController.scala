package controllers

import javax.inject._
import play.api.libs.json.JsValue.jsValueToJsLookup
import play.api.mvc._

/**
 * Return text for a POST request to http://localhost:9000/json
 * Example:
 * <pre>
 * curl -X POST \
 * -H "Content-Type: application/json" \
 * -d '{"name": "John"}' \
 * http://localhost:9000/json
 * </pre>
 */
@Singleton
class PostJsonController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {

  case class RequestBody(name: String)

  def json(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    val jsonValue = request.body.asJson.get
    val name = (jsonValue \ "name").asOpt[String].get
    val nicknameList = (jsonValue \ "nicknames").asOpt[List[String]].get
    val nicknameStr = nicknameList.mkString(", ")
    val content = f"Hello, $name ($nicknameStr)!"
    Ok(content)
  }
}

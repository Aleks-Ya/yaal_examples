package controllers.json

import javax.inject._
import play.api.libs.json.JsValue.jsValueToJsLookup
import play.api.mvc._

/**
 * Return text for a POST request to http://localhost:9000/sync/post/json
 * Example:
 * <pre>
 * curl -X POST \
 * -H "Content-Type: application/json" \
 * -d '{"name": "John", "nicknames": ["Alpha", "Lord"]}' \
 * http://localhost:9000/json
 * </pre>
 */
@Singleton
class JsonRequestPostSyncController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {

  case class RequestBody(name: String)

  def json(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    val jsonValue = request.body.asJson.get
    val name = (jsonValue \ "name").asOpt[String].get
    val nicknameList = (jsonValue \ "nicknames").asOpt[List[String]].get
    val nicknameStr = nicknameList.mkString(", ")
    Ok(f"Hello, $name ($nicknameStr)!")
  }
}

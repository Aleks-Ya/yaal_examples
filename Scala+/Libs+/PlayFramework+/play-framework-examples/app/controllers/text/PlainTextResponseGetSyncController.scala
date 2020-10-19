package controllers.text

import javax.inject._
import play.api.mvc._

/**
 * Return text for a GET request to http://localhost:9000/sync/get/text
 */
@Singleton
class PlainTextResponseGetSyncController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {

  def text(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    Ok("Hello Content!")
  }
}

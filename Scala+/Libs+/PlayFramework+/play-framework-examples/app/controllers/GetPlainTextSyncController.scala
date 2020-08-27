package controllers

import javax.inject._
import play.api.mvc._

/**
 * Return text for a GET request to http://localhost:9000/sync/get/text
 */
@Singleton
class GetPlainTextSyncController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {

  def text(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    Ok("Hello Content!")
  }
}

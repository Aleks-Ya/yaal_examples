package controllers

import javax.inject._
import play.api.mvc._

/**
 * Return text for a GET request to http://localhost:9000/text/get
 */
@Singleton
class GetTextController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {

  def text(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    val content = "Hello Content!"
    Ok(content)
  }
}

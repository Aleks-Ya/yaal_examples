package controllers

import javax.inject._
import play.api.mvc._

@Singleton
class GetController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {

  def text(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    Ok("Hello Content!")
  }
}

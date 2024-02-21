package controllers

import controllers.GetController.BODY
import javax.inject._
import play.api.mvc._

@Singleton
class GetController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {

  def text(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    Ok(BODY)
  }
}

object GetController {
  val BODY = "Hello Content!"
}

package controllers

import controllers.HomeController.HOME_BODY
import javax.inject._
import play.api.mvc._

@Singleton
class HomeController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {
  def index(): Action[AnyContent] = Action { implicit request: Request[AnyContent] => Ok(HOME_BODY) }
}

object HomeController {
  val HOME_BODY =  "The Home Body"
}

package controllers.ssl

import controllers.ssl.SslController.BODY
import javax.inject._
import play.api.mvc._

@Singleton
class SslController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {
  def ssl(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    Ok(BODY)
  }
}

object SslController {
  val BODY = """{"abc":1}"""
}

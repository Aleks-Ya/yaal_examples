package controllers

import javax.inject._
import play.api.mvc._

/**
 * Extract parts of URL path.
 */
@Singleton
class DynamicPathController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {
  def returnId(id: String): Action[AnyContent] = Action { implicit request: Request[AnyContent] => Ok(id) }
}

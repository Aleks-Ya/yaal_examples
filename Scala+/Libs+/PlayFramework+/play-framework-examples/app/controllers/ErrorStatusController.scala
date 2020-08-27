package controllers

import javax.inject._
import play.api.mvc._

/**
 * Return error status for a GET request.
 */
@Singleton
class ErrorStatusController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {

  /**
   * Return a 400 "Bad Request" status for a GET request.
   * Example:
   * <pre>
   * curl -I http://localhost:9000/error/bad
   * </pre>
   */
  def badRequest(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    val content = "Your request is bad!"
    BadRequest(content)
  }

  /**
   * Return a 500 "Internal Server  Error" status for a GET request.
   * Example:
   * <pre>
   * curl -I http://localhost:9000/error/server
   * </pre>
   */
  def serverError(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    val content = "An internal server error happened!"
    InternalServerError(content)
  }
}

package controllers

import javax.inject._
import play.api.mvc._

/**
 * Return error status for a GET request.
 */
@Singleton
class ErrorStatusSyncController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {

  /**
   * Return a 400 "Bad Request" status for a GET request.
   * Example:
   * <pre>
   * curl -I http://localhost:9000/sync/error/bad
   * </pre>
   */
  def badRequest(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    BadRequest("Your request is bad!")
  }

  /**
   * Return a 500 "Internal Server  Error" status for a GET request.
   * Example:
   * <pre>
   * curl -I http://localhost:9000/sync/error/server
   * </pre>
   */
  def serverError(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    InternalServerError("An internal server error happened!")
  }
}

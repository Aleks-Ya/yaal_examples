package controllers.error

import javax.inject._
import play.api.mvc._

import scala.concurrent.{ExecutionContext, Future}

/**
 * Return error status for a GET request asynchronously.
 * Example:
 * <pre>
 * curl http://localhost:9000/async/error/bad
 * </pre>
 */
@Singleton
class ErrorStatusAsyncController @Inject()(val controllerComponents: ControllerComponents)(implicit ec: ExecutionContext) extends BaseController {

  def computePIAsynchronously(): Future[Double] = Future {
    Thread.sleep(5000)
    throw new IllegalStateException("cannot compute PI value")
  }

  def text(): Action[AnyContent] = Action.async { implicit request: Request[AnyContent] =>
    val futurePIValue: Future[Double] = computePIAsynchronously()
    val futureResult: Future[Result] = futurePIValue.map { pi =>
      Ok("PI value computed: " + pi)
    }.recover {
      case e: IllegalStateException =>
        BadRequest(s"Your request is bad: ${e.getMessage}")
    }
    futureResult
  }


}

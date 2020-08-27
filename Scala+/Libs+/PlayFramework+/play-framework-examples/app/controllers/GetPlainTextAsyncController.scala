package controllers

import javax.inject._
import play.api.mvc._

import scala.concurrent.{ExecutionContext, Future}

/**
 * Return text for a GET request asynchronously.
 * Example:
 * <pre>
 * curl http://localhost:9000/async/text/get
 * </pre>
 */
@Singleton
class GetPlainTextAsyncController @Inject()(val controllerComponents: ControllerComponents)(implicit ec: ExecutionContext) extends BaseController {

  def computePIAsynchronously(): Future[Double] = Future {
    Thread.sleep(5000)
    3.14D
  }

  def text(): Action[AnyContent] = Action.async { implicit request: Request[AnyContent] =>
    val futurePIValue: Future[Double] = computePIAsynchronously()
    val futureResult: Future[Result] = futurePIValue.map { pi =>
      Ok("PI value computed: " + pi)
    }
    futureResult
  }
}

package controllers.error

import org.scalatestplus.play._
import org.scalatestplus.play.guice._
import play.api.test.Helpers._
import play.api.test._
import play.mvc.Http.MimeTypes

import scala.concurrent.ExecutionContext.Implicits.global

class ErrorStatusAsyncControllerSpec extends PlaySpec with GuiceOneAppPerTest with Injecting {

  "ErrorStatusAsyncController GET" should {

    "return 400 status" in {
      val controller = new ErrorStatusAsyncController(stubControllerComponents())
      val text = controller.text().apply(FakeRequest(GET, "/"))
      status(text) mustBe BAD_REQUEST
      contentType(text) mustBe Some(MimeTypes.TEXT)
      contentAsString(text) must include("Your request is bad: cannot compute PI value")
    }

  }
}

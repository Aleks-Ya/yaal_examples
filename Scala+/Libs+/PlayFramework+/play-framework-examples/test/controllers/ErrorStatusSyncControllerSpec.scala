package controllers

import org.scalatestplus.play._
import org.scalatestplus.play.guice._
import play.api.test.Helpers._
import play.api.test._
import play.mvc.Http.MimeTypes

class ErrorStatusSyncControllerSpec extends PlaySpec with GuiceOneAppPerTest with Injecting {

  "ErrorStatusSyncController GET" should {

    "return 400 status" in {
      val controller = new ErrorStatusSyncController(stubControllerComponents())
      val text = controller.badRequest().apply(FakeRequest(GET, "/"))
      status(text) mustBe BAD_REQUEST
      contentType(text) mustBe Some(MimeTypes.TEXT)
      contentAsString(text) must include("Your request is bad!")
    }

    "return 500 status" in {
      val controller = new ErrorStatusSyncController(stubControllerComponents())
      val text = controller.serverError().apply(FakeRequest(GET, "/"))
      status(text) mustBe INTERNAL_SERVER_ERROR
      contentType(text) mustBe Some(MimeTypes.TEXT)
      contentAsString(text) must include("An internal server error happened!")
    }

  }
}

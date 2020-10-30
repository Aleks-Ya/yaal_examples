package controllers

import org.scalatestplus.play._
import org.scalatestplus.play.guice._
import play.api.test.Helpers._
import play.api.test._
import play.mvc.Http.MimeTypes

class GetControllerSpec extends PlaySpec with GuiceOneAppPerTest with Injecting {

  "GetPlainTextSyncController GET" should {

    "return greeting message" in {
      val controller = new GetController(stubControllerComponents())
      val text = controller.text().apply(FakeRequest(GET, "/"))
      status(text) mustBe OK
      contentType(text) mustBe Some(MimeTypes.TEXT)
      contentAsString(text) must include("Hello Content!")
    }

  }
}

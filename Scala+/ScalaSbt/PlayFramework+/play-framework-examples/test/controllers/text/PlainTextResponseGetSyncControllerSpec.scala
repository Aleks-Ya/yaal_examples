package controllers.text

import org.scalatestplus.play._
import org.scalatestplus.play.guice._
import play.api.test.Helpers._
import play.api.test._
import play.mvc.Http.MimeTypes

class PlainTextResponseGetSyncControllerSpec extends PlaySpec with GuiceOneAppPerTest with Injecting {

  "GetPlainTextSyncController GET" should {

    "return greeting message" in {
      val controller = app.injector.instanceOf[PlainTextResponseGetSyncController]
      val text = controller.text().apply(FakeRequest(GET, "/"))
      status(text) mustBe OK
      contentType(text) mustBe Some(MimeTypes.TEXT)
      contentAsString(text) must include("Hello Content!")
    }

  }
}

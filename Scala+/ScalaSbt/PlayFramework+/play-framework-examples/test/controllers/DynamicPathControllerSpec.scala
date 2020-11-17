package controllers

import org.scalatestplus.play._
import org.scalatestplus.play.guice._
import play.api.test.Helpers._
import play.api.test._
import play.mvc.Http.MimeTypes

class DynamicPathControllerSpec extends PlaySpec with GuiceOneAppPerTest with Injecting {

  "DynamicPathController GET" should {

    "return ID from URL path" in {
      val controller = app.injector.instanceOf[DynamicPathController]
      val id = "1"
      val text = controller.returnId(id).apply(FakeRequest(GET, "/2"))
      status(text) mustBe OK
      contentType(text) mustBe Some(MimeTypes.TEXT)
      contentAsString(text) must include(id)
    }

  }
}

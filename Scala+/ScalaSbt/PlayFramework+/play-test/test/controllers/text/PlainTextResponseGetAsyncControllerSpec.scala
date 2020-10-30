package controllers.text

import org.scalatestplus.play._
import org.scalatestplus.play.guice._
import play.api.test.Helpers._
import play.api.test._
import play.mvc.Http.MimeTypes

import scala.concurrent.ExecutionContext.Implicits.global


class PlainTextResponseGetAsyncControllerSpec extends PlaySpec with GuiceOneAppPerTest with Injecting {

  "GetPlainTextAsyncController GET" should {

    "calculate PI number" in {
      val controller = new PlainTextResponseGetAsyncController(stubControllerComponents())
      val text = controller.text().apply(FakeRequest(GET, "/"))
      status(text) mustBe OK
      contentType(text) mustBe Some(MimeTypes.TEXT)
      contentAsString(text) must include ("PI value computed: 3.14")
    }

  }
}

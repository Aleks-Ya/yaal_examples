package controllers.json

import org.scalatestplus.play._
import org.scalatestplus.play.guice._
import play.api.test.Helpers._
import play.api.test._
import play.mvc.Http.MimeTypes

class JsonResponseGetSyncControllerSpec extends PlaySpec with GuiceOneAppPerTest with Injecting {

  "ResponseJsonSyncController GET" should {
    "form JSON body from an object" in {
      val controller = new JsonResponseGetSyncController(stubControllerComponents())
      val text = controller.json().apply(FakeRequest(GET, "/"))
      status(text) mustBe OK
      contentType(text) mustBe Some(MimeTypes.TEXT)
      contentAsString(text) must include("""{"name":"John","age":30}""")
    }

  }
}

package controllers.json

import org.scalatestplus.play._
import org.scalatestplus.play.guice._
import play.api.test.Helpers._
import play.api.test._
import play.mvc.Http.MimeTypes

class JsonResponseGetSyncControllerSpec extends PlaySpec with GuiceOneAppPerTest with Injecting {

  "ResponseJsonSyncController GET" should {
    "form JSON body from an object" in {
      val controller = app.injector.instanceOf[JsonResponseGetSyncController]
      val result = controller.json().apply(FakeRequest(GET, "/"))
      status(result) mustBe OK
      contentType(result) mustBe Some(MimeTypes.TEXT)
      contentAsString(result) must include("""{"name":"John","age":30}""")
    }

  }
}

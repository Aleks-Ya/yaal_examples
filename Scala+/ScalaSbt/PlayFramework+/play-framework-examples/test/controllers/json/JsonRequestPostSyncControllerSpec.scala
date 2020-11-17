package controllers.json

import org.scalatestplus.play._
import org.scalatestplus.play.guice._
import play.api.libs.json.Json
import play.api.test.Helpers._
import play.api.test._
import play.mvc.Http.MimeTypes

class JsonRequestPostSyncControllerSpec extends PlaySpec with GuiceOneAppPerTest with Injecting {

  "PostJsonSyncController GET" should {

    "accept JSON request with names and return a greeting" in {
      val controller = app.injector.instanceOf[JsonRequestPostSyncController]
      val text = controller.json().apply(FakeRequest(POST, "/")
        .withJsonBody(Json.parse("""{"name": "John", "nicknames": ["Alpha", "Lord"]}""")))
      status(text) mustBe OK
      contentType(text) mustBe Some(MimeTypes.TEXT)
      contentAsString(text) must include("Hello, John (Alpha, Lord)!")
    }

  }
}

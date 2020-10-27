package controllers

import org.scalatestplus.play._
import org.scalatestplus.play.guice._
import play.api.test.Helpers._
import play.api.test._
import play.mvc.Http.MimeTypes

class PersonControllerSpec extends PlaySpec with GuiceOneAppPerTest with Injecting {

  "PersonController GET" should {

    "return Persons form database" in {
      val controller = new PersonController(stubControllerComponents())
      val text = controller.text().apply(FakeRequest(GET, "/"))
      status(text) mustBe OK
      contentType(text) mustBe Some(MimeTypes.TEXT)
      contentAsString(text) must include("Hello Content!")
    }

  }
}

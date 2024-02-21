package controllers

import org.junit.Assert.assertEquals
import org.junit.Test
import play.api.libs.json.Json
import play.api.test.Helpers._
import play.api.test._
import play.mvc.Http.MimeTypes

class PostControllerTest {

  @Test
  def post(): Unit = {
    val controller = new PostController(stubControllerComponents())
    val text = controller.json().apply(FakeRequest(POST, "/")
      .withJsonBody(Json.parse("""{"name": "John", "nicknames": ["Alpha", "Lord"]}""")))
    assertEquals(OK, status(text))
    assertEquals(Some(MimeTypes.TEXT), contentType(text))
    assertEquals("Hello, John (Alpha, Lord)!", contentAsString(text))
  }
}

package controllers

import org.scalatestplus.play._
import play.api.libs.json.{Json, OFormat}
import play.api.test.Helpers._
import play.api.test._
import play.mvc.Http.MimeTypes

class PersonControllerSpec extends PlaySpec {

  "PersonController GET" should {

    "return all Persons form database" in {
      val personDao = new PersonDao()
      val personService = new PersonService(personDao)
      val controller = new PersonController(stubControllerComponents(), personService)
      val getResultFuture = controller.getPersons().apply(FakeRequest(GET, "/"))
      status(getResultFuture) mustBe OK
      contentType(getResultFuture) mustBe Some(MimeTypes.JSON)
      contentAsString(getResultFuture) must include("""[{"id":1,"name":"John"},{"id":2,"name":"Mary"}]""")
    }

    "create Person in database" in {
      val personDao = new PersonDao()
      val personService = new PersonService(personDao)
      val controller = new PersonController(stubControllerComponents(), personService)

      implicit val personFormat: OFormat[Person] = Json.format[Person]
      val personJson = Json.toJson(Person(1, "John"))
      val createResultFuture = controller.createPerson().apply(FakeRequest(POST, "/").withJsonBody(personJson))
      status(createResultFuture) mustBe OK

      val getResultFuture = controller.getPersons().apply(FakeRequest(GET, "/"))
      status(getResultFuture) mustBe OK
      contentType(getResultFuture) mustBe Some(MimeTypes.JSON)
      contentAsString(getResultFuture) must include("""[{"id":1,"name":"John"}]""")
    }

    "delete Person in database" in {
      val personDao = new PersonDao()
      val personService = new PersonService(personDao)
      val controller = new PersonController(stubControllerComponents(), personService)

      implicit val personFormat: OFormat[Person] = Json.format[Person]
      val personId = 1
      val personJson = Json.toJson(Person(personId, "John"))
      val createResultFuture = controller.createPerson().apply(FakeRequest(POST, "/").withJsonBody(personJson))
      status(createResultFuture) mustBe OK

      val getResultFuture = controller.getPersons().apply(FakeRequest(GET, "/"))
      contentAsString(getResultFuture) must include("""[{"id":1,"name":"John"}]""")

      val deleteResultFuture = controller.deletePerson().apply(FakeRequest(DELETE, personId.toString))
      status(deleteResultFuture) mustBe OK

      val getResultFuture2 = controller.getPersons().apply(FakeRequest(GET, "/"))
      contentAsString(getResultFuture2) must include("""[]""")
    }

  }
}
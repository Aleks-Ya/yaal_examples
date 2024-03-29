package controllers

import org.scalatest.TestData
import org.scalatestplus.play._
import org.scalatestplus.play.guice.GuiceOneAppPerTest
import play.api.Application
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.libs.json.{Json, OFormat}
import play.api.test.Helpers._
import play.api.test._
import play.mvc.Http.MimeTypes

import scala.util.Random

class PlayLiquibaseSpec extends PlaySpec with GuiceOneAppPerTest with Injecting {

  implicit override def newAppForTest(testData: TestData): Application = {
    val driver = "org.h2.Driver"
    val url = s"jdbc:h2:mem:test${Random.nextInt()}:test;DATABASE_TO_UPPER=false;DB_CLOSE_DELAY=-1"
    val app = new GuiceApplicationBuilder()
      .configure(Map(
        "db.default.driver" -> driver,
        "db.default.url" -> url,
        "db.default.username" -> "sa",
        "db.default.password" -> "",
        "liquibase.changelog" -> getClass.getClassLoader.getResource("liquibase/changelog.xml").getFile
      )).build()
    app
  }

  "PersonController GET" should {

    "initialize Liquibase and return all Persons form database" in {
      val controller = app.injector.instanceOf[PlayLiquibasePersonController]
      val getResultFuture = controller.getPersons().apply(FakeRequest(GET, "/"))
      status(getResultFuture) mustBe OK
      contentType(getResultFuture) mustBe Some(MimeTypes.JSON)
      contentAsString(getResultFuture) must include("""[{"id":1,"name":"John"},{"id":2,"name":"Mary"}]""")
    }

    "initialize Liquibase and create Person in database" in {
      val controller = app.injector.instanceOf[PlayLiquibasePersonController]

      implicit val personFormat: OFormat[PlayLiquibasePerson] = Json.format[PlayLiquibasePerson]
      val personJson = Json.toJson(PlayLiquibasePerson(3, "John3"))
      val createResultFuture = controller.createPerson().apply(FakeRequest(POST, "/").withJsonBody(personJson))
      status(createResultFuture) mustBe OK

      val getResultFuture = controller.getPersons().apply(FakeRequest(GET, "/"))
      status(getResultFuture) mustBe OK
      contentType(getResultFuture) mustBe Some(MimeTypes.JSON)
      contentAsString(getResultFuture) must include("""[{"id":1,"name":"John"},{"id":2,"name":"Mary"},{"id":3,"name":"John3"}]""")
    }

    "initialize Liquibase and delete Person in database" in {
      val controller = app.injector.instanceOf[PlayLiquibasePersonController]

      implicit val personFormat: OFormat[PlayLiquibasePerson] = Json.format[PlayLiquibasePerson]
      val personId = 4
      val personJson = Json.toJson(PlayLiquibasePerson(personId, "John4"))
      val createResultFuture = controller.createPerson().apply(FakeRequest(POST, "/").withJsonBody(personJson))
      status(createResultFuture) mustBe OK

      val getResultFuture = controller.getPersons().apply(FakeRequest(GET, "/"))
      contentAsString(getResultFuture) must include("""[{"id":1,"name":"John"},{"id":2,"name":"Mary"},{"id":4,"name":"John4"}]""")

      val deleteResultFuture = controller.deletePerson().apply(FakeRequest(DELETE, personId.toString))
      status(deleteResultFuture) mustBe OK

      val getResultFuture2 = controller.getPersons().apply(FakeRequest(GET, "/"))
      contentAsString(getResultFuture2) must include("""[{"id":1,"name":"John"},{"id":2,"name":"Mary"}]""")
    }

  }
}

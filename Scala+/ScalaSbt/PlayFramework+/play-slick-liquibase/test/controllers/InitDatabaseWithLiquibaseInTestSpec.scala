package controllers

import play.api.test.Helpers._
import play.api.test._
import play.mvc.Http.MimeTypes
import slick.jdbc.H2Profile.api._
import slick.lifted.TableQuery

import scala.concurrent.Await
import scala.concurrent.duration.Duration

class InitDatabaseWithLiquibaseInTestSpec extends H2Liquibase {

  "Liquibase Unit Test" should {
    "add Persons from changelog.xml and from code and select them all" in {
      val personsQuery = TableQuery[PersonTable]
      Await.result(db.run(DBIO.seq(
        personsQuery += Person(3, "John"),
        personsQuery += Person(4, "Mary"),
      )), Duration.Inf)

      val personDao = new PersonDao(db)
      val personService = new PersonService(personDao)
      val controller = new PersonController(stubControllerComponents(), personService)
      val getResultFuture = controller.getPersons().apply(FakeRequest(GET, "/"))
      status(getResultFuture) mustBe OK
      contentType(getResultFuture) mustBe Some(MimeTypes.JSON)
      contentAsString(getResultFuture) must include("""[{"id":1,"name":"Mark"},{"id":2,"name":"Mike"},{"id":3,"name":"John"},{"id":4,"name":"Mary"}]""")
    }
  }
}

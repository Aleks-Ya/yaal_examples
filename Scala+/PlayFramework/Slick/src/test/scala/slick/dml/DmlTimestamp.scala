package slick.dml

import java.time.{Instant, ZonedDateTime}

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import slick.H2Helper
import slick.jdbc.H2Profile.api._
import slick.lifted.Tag

import scala.concurrent.Await
import scala.concurrent.duration.Duration

class DmlTimestamp extends AnyFlatSpec with Matchers with H2Helper {

  it should "map VARCHAR to ZonedDateTime" in {
    val db = h2Database

    case class Person(id: Int, name: String, eventTime: ZonedDateTime)

    class PersonTable(tag: Tag) extends Table[Person](tag, "PERSON_LIST") {
      def id = column[Int]("ID", O.PrimaryKey)

      def name = column[String]("NAME")

      def eventTime = column[ZonedDateTime]("EVENT_TIME")

      def * = (id, name, eventTime).mapTo[Person]
    }

    try {
      val personsQuery = TableQuery[PersonTable]

      val createTableAction = DBIO.seq(
        personsQuery.schema.create
      )
      val createTableFuture = db.run(createTableAction)
      Await.result(createTableFuture, Duration.Inf)

      val eventTime1 = ZonedDateTime.parse("2007-12-03T10:15:30+01:00[Europe/Paris]")
      val eventTime2 = ZonedDateTime.parse("2020-10-17T18:40:35+01:00[Europe/Paris]")

      val expPerson1 = Person(1, "John", eventTime1)
      val expPerson3 = Person(2, "Mary", eventTime2)
      val insertAction = DBIO.seq(
        personsQuery += expPerson1,
        personsQuery += expPerson3,
      )
      val insertFuture = db.run(insertAction)
      Await.result(insertFuture, Duration.Inf)

      val select1Future = db.run(personsQuery.result)
      val res1 = Await.result(select1Future, Duration.Inf)
      res1 should contain allOf(expPerson1, expPerson3)

      val deleteQuery = personsQuery.filter(_.id === expPerson3.id)
      val deleteAction = deleteQuery.delete
      val deleteFuture = db.run(deleteAction)
      Await.result(deleteFuture, Duration.Inf)

      val select2Future = db.run(personsQuery.result)
      val select2Result = Await.result(select2Future, Duration.Inf)
      select2Result should contain(expPerson1)

    } finally db.close
  }

  it should "map 'timestamp with time zone' to Instant" in {
    val db = h2Database

    case class Person(id: Int, name: String, eventTime: Instant)

    class PersonTable(tag: Tag) extends Table[Person](tag, "PERSON_LIST") {
      def id = column[Int]("ID", O.PrimaryKey)

      def name = column[String]("NAME")

      def eventTime = column[Instant]("EVENT_TIME")

      def * = (id, name, eventTime).mapTo[Person]
    }

    try {
      val personsQuery = TableQuery[PersonTable]

      val createTableAction = DBIO.seq(
        sqlu"create table PERSON_LIST (ID INTEGER, NAME VARCHAR, EVENT_TIME TIMESTAMP WITH TIME ZONE)"
      )
      val createTableFuture = db.run(createTableAction)
      Await.result(createTableFuture, Duration.Inf)

      val eventTime1 = Instant.parse("2007-12-03T10:15:30Z")
      val eventTime2 = Instant.parse("2020-10-17T18:40:35Z")

      val expPerson1 = Person(1, "John", eventTime1)
      val expPerson3 = Person(2, "Mary", eventTime2)
      val insertAction = DBIO.seq(
        personsQuery += expPerson1,
        personsQuery += expPerson3,
      )
      val insertFuture = db.run(insertAction)
      Await.result(insertFuture, Duration.Inf)

      val select1Future = db.run(personsQuery.result)
      val res1 = Await.result(select1Future, Duration.Inf)
      res1 should contain allOf(expPerson1, expPerson3)

      val deleteQuery = personsQuery.filter(_.id === expPerson3.id)
      val deleteAction = deleteQuery.delete
      val deleteFuture = db.run(deleteAction)
      Await.result(deleteFuture, Duration.Inf)

      val select2Future = db.run(personsQuery.result)
      val select2Result = Await.result(select2Future, Duration.Inf)
      select2Result should contain(expPerson1)

    } finally db.close
  }


}



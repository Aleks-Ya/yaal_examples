package slick.dml

import java.util.concurrent.TimeUnit

import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import slick.jdbc.H2Profile.api._
import slick.lifted.Tag

import scala.concurrent.Await
import scala.concurrent.duration.Duration

class DmlMapToPrimitiveSeq extends AnyFlatSpec with Matchers {

  it should "insert, select, delete (map to primitive seq)" in {
    val db = Database.forURL(url = "jdbc:h2:mem:test1", driver = "org.h2.Driver", keepAliveConnection = true)

    try {
      class Persons(tag: Tag) extends Table[(Int, String, Int)](tag, "PERSONS") {
        def id = column[Int]("ID", O.PrimaryKey)

        def name = column[String]("NAME")

        def age = column[Int]("AGE")

        def * = (id, name, age)
      }

      val personsQuery = TableQuery[Persons]

      val createTableAction = DBIO.seq(
        personsQuery.schema.create
      )
      val createTableFuture = db.run(createTableAction)
      Await.result(createTableFuture, Duration.Inf)

      val insertAction = DBIO.seq(
        personsQuery += (1, "John", 30),
        personsQuery += (2, "Mary", 20),
      )
      val insertFuture = db.run(insertAction)
      Await.result(insertFuture, Duration.Inf)

      val select1Future = db.run(personsQuery.result)
      val res1 = Await.result(select1Future, Duration.Inf)
      assertThat(res1.mkString, equalTo("(1,John,30)(2,Mary,20)"))

      val deleteQuery = personsQuery.filter(_.id === 2)
      val deleteAction = deleteQuery.delete
      val deleteFuture = db.run(deleteAction)
      Await.result(deleteFuture, Duration.Inf)

      val select2Query = personsQuery.map(_.name)
      val select2Action = select2Query.result
      val select2Future = db.run(select2Action)
      val select2Result = Await.result(select2Future, Duration.Inf)
      assertThat(select2Result.mkString, equalTo("John"))

    } finally db.close
  }
}

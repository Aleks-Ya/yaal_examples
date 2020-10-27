package slick.dml

import java.util.concurrent.TimeUnit

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import slick.jdbc.H2Profile.api._

import scala.concurrent.Await
import scala.concurrent.duration.Duration

class DmlMapToObject extends AnyFlatSpec with Matchers {

   it should "insert, select, delete (map to object)" in {
    val db = Database.forURL(url = "jdbc:h2:mem:test1", driver = "org.h2.Driver", keepAliveConnection = true)

    try {
      val personsQuery = TableQuery[PersonTable]

      val createTableAction = DBIO.seq(
        personsQuery.schema.create
      )
      val createTableFuture = db.run(createTableAction)
      Await.result(createTableFuture, Duration(1000, TimeUnit.SECONDS))

      val expPerson1 = Person(1, "John", 30)
      val expPerson2 = Person(2, "Mary", 20)
      val insertAction = DBIO.seq(
        personsQuery += expPerson1,
        personsQuery += expPerson2,
      )
      val insertFuture = db.run(insertAction)
      Await.result(insertFuture, Duration(1000, TimeUnit.SECONDS))

      val select1Future = db.run(personsQuery.result)
      val res1 = Await.result(select1Future, Duration(1000, TimeUnit.SECONDS))
      res1 should contain allOf(expPerson1, expPerson2)

      val deleteQuery = personsQuery.filter(_.id === expPerson2.id)
      val deleteAction = deleteQuery.delete
      val deleteFuture = db.run(deleteAction)
      Await.result(deleteFuture, Duration(1000, TimeUnit.SECONDS))

      val select2Future = db.run(personsQuery.result)
      val select2Result = Await.result(select2Future, Duration(1000, TimeUnit.SECONDS))
      select2Result should contain(expPerson1)

    } finally db.close
  }

}

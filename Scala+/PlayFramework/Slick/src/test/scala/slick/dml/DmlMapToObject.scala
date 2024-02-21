package slick.dml

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import slick.H2Helper
import slick.jdbc.H2Profile.api._

import scala.concurrent.Await
import scala.concurrent.duration.Duration

class DmlMapToObject extends AnyFlatSpec with Matchers with H2Helper {

  it should "insert, select, delete (map to object)" in {
    val db = h2Database

    try {
      val personsQuery = TableQuery[PersonTable]

      val createTableAction = DBIO.seq(
        personsQuery.schema.create
      )
      val createTableFuture = db.run(createTableAction)
      Await.result(createTableFuture, Duration.Inf)

      val expPerson1 = Person(1, "John", 30)
      val expPerson2 = Person(2, "Mary", 20)
      val insertAction = DBIO.seq(
        personsQuery += expPerson1,
        personsQuery += expPerson2,
      )
      val insertFuture = db.run(insertAction)
      Await.result(insertFuture, Duration.Inf)

      val select1Future = db.run(personsQuery.result)
      val res1 = Await.result(select1Future, Duration.Inf)
      res1 should contain allOf(expPerson1, expPerson2)

      val deleteQuery = personsQuery.filter(_.id === expPerson2.id)
      val deleteAction = deleteQuery.delete
      val deleteFuture = db.run(deleteAction)
      Await.result(deleteFuture, Duration.Inf)

      val select2Future = db.run(personsQuery.result)
      val select2Result = Await.result(select2Future, Duration.Inf)
      select2Result should contain(expPerson1)

    } finally db.close
  }

}

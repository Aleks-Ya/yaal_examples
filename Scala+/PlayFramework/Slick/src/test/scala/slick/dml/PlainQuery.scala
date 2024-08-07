package slick.dml

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import slick.H2Helper
import slick.jdbc.H2Profile.api._

import scala.concurrent.Await
import scala.concurrent.duration.Duration

class PlainQuery extends AnyFlatSpec with Matchers with H2Helper {

  it should "init database and insert with plain queries" in {
    val db = h2Database

    try {
      val personsQuery = TableQuery[PersonTable]

      val createTableAction = DBIO.seq(
        sqlu"create table PERSON_LIST (ID INTEGER, NAME VARCHAR, AGE INTEGER)"
      )
      val createTableFuture = db.run(createTableAction)
      Await.result(createTableFuture, Duration.Inf)

      val insertAction = DBIO.seq(
        sqlu"insert into PERSON_LIST values(1, 'John', 30)",
        sqlu"insert into PERSON_LIST values(2, 'Mary', 20)",
        sqlu"insert into PERSON_LIST values(3, 'Mike', 10)"
      )
      val insertFuture = db.run(insertAction)
      Await.result(insertFuture, Duration.Inf)

      val expPerson1 = Person(1, "John", 30)
      val expPerson2 = Person(2, "Mary", 20)
      val expPerson3 = Person(3, "Mike", 10)

      val select1Future = db.run(personsQuery.result)
      val res1 = Await.result(select1Future, Duration.Inf)
      res1 should contain allOf(expPerson1, expPerson2, expPerson3)

      val deleteQuery = personsQuery.filter(_.id === expPerson2.id)
      val deleteAction = deleteQuery.delete
      val deleteFuture = db.run(deleteAction)
      Await.result(deleteFuture, Duration.Inf)

      val select2Future = db.run(personsQuery.result)
      val select2Result = Await.result(select2Future, Duration.Inf)
      select2Result should contain allOf(expPerson1, expPerson3)

    } finally db.close
  }

  it should "combine plain queries and object mapping" in {
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
      val expPerson3 = Person(3, "Mike", 10)
      val insertAction = DBIO.seq(
        personsQuery += expPerson1,
        personsQuery += expPerson2,
        sqlu"insert into PERSON_LIST values(3, 'Mike', 10)",
      )
      val insertFuture = db.run(insertAction)
      Await.result(insertFuture, Duration.Inf)

      val select1Future = db.run(personsQuery.result)
      val res1 = Await.result(select1Future, Duration.Inf)
      res1 should contain allOf(expPerson1, expPerson2, expPerson3)

      val deleteQuery = personsQuery.filter(_.id === expPerson2.id)
      val deleteAction = deleteQuery.delete
      val deleteFuture = db.run(deleteAction)
      Await.result(deleteFuture, Duration.Inf)

      val select2Future = db.run(personsQuery.result)
      val select2Result = Await.result(select2Future, Duration.Inf)
      select2Result should contain allOf(expPerson1, expPerson3)

    } finally db.close
  }

}

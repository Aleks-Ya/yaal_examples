package controllers

import javax.inject.{Inject, Singleton}
import play.api.db.Database

import scala.collection.mutable
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

@Singleton
class PersonDao @Inject()(db: Database) {

  def getAll: Future[Seq[Person]] = Future {
    db.withConnection { conn =>
      val statement = conn.createStatement()
      val resultSet = statement.executeQuery("select id, name from person")
      val s: mutable.MutableList[Person] = mutable.MutableList()
      while (resultSet.next()) {
        s += Person(resultSet.getInt(1), resultSet.getString(2))
      }
      s
    }
  }

  def create(person: Person): Future[Unit] = Future {
    db.withConnection { conn =>
      val statement = conn.createStatement()
      statement.executeUpdate(s"insert into person(id, name) values (${person.id},'${person.name}')")
    }
  }

  def delete(personId: Int): Future[Int] = Future {
    db.withConnection { conn =>
      val statement = conn.createStatement()
      statement.executeUpdate(s"delete from person where id=$personId")
      personId
    }
  }
}

package controllers

import javax.inject.{Inject, Singleton}
import play.api.db.Database

import scala.collection.mutable
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

@Singleton
class PlayLiquibasePersonDao @Inject()(db: Database) {

  def getAll: Future[Seq[PlayLiquibasePerson]] = Future {
    db.withConnection { conn =>
      val statement = conn.createStatement()
      val resultSet = statement.executeQuery("select id, name from person")
      val s: mutable.MutableList[PlayLiquibasePerson] = mutable.MutableList()
      while (resultSet.next()) {
        s += PlayLiquibasePerson(resultSet.getInt(1), resultSet.getString(2))
      }
      s
    }
  }

  def create(person: PlayLiquibasePerson): Future[Unit] = Future {
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

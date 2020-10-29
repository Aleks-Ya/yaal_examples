package controllers

import javax.inject.{Inject, Singleton}
import play.api.db.Database

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

@Singleton
class PersonDao @Inject()(db: Database) {

  def getAll: Seq[Person] = {
    db.withConnection { conn =>
      val statement = conn.createStatement()
      val resultSet = statement.executeQuery("select id, name from person")
      new RsIterator(resultSet).map(row => Person(row.getInt(1), row.getString(2))).toSeq
    }
  }

  def create(person: Person): Future[Unit] = Future()

  def delete(personId: Int): Future[Int] = Future(33)
}

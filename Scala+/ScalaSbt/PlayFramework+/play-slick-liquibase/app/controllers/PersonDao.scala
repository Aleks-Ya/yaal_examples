package controllers

import javax.inject.{Inject, Singleton}
import play.api.db.slick.DatabaseConfigProvider
import slick.basic.DatabaseConfig
import slick.jdbc.H2Profile.api._
import slick.jdbc.JdbcProfile

import scala.concurrent.Future

@Singleton
class PersonDao @Inject()(private val dbConfigProvider: DatabaseConfigProvider) {
  private val dbConfig: DatabaseConfig[JdbcProfile] = dbConfigProvider.get[JdbcProfile]
  private val personQuery: TableQuery[PersonTable] = TableQuery[PersonTable]

  def getAll: Future[Seq[Person]] = dbConfig.db.run(personQuery.result)

  def create(person: Person): Future[Unit] = dbConfig.db.run(DBIO.seq(personQuery += person))

  def delete(personId: Int): Future[Int] = dbConfig.db.run(personQuery.filter(_.id === personId).delete)
}

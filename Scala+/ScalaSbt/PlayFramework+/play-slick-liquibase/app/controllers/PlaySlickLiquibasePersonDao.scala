package controllers

import javax.inject.{Inject, Singleton}
import play.api.db.slick.DatabaseConfigProvider
import slick.basic.DatabaseConfig
import slick.jdbc.H2Profile.api._
import slick.jdbc.JdbcProfile

import scala.concurrent.Future

@Singleton
class PlaySlickLiquibasePersonDao @Inject()(private val db: Database) {
  private val personQuery: TableQuery[PlaySlickLiquibasePersonTable] = TableQuery[PlaySlickLiquibasePersonTable]

  def getAll: Future[Seq[PlaySlickLiquibasePerson]] = db.run(personQuery.result)

  def create(person: PlaySlickLiquibasePerson): Future[Unit] = db.run(DBIO.seq(personQuery += person))

  def delete(personId: Int): Future[Int] = db.run(personQuery.filter(_.id === personId).delete)
}

package controllers

import javax.inject.{Inject, Singleton}
import slick.jdbc.H2Profile.api._

import scala.concurrent.Future

@Singleton
class PlaySlickLiquibasePersonDao @Inject()(private val db: Database) {
  private val personQuery: TableQuery[PlaySlickLiquibasePersonTable] = TableQuery[PlaySlickLiquibasePersonTable]

  def getAll: Future[Seq[PlaySlickLiquibasePerson]] = db.run(personQuery.result)

  def create(person: PlaySlickLiquibasePerson): Future[Unit] = db.run(DBIO.seq(personQuery += person))

  def delete(personId: Int): Future[Int] = db.run(personQuery.filter(_.id === personId).delete)
}

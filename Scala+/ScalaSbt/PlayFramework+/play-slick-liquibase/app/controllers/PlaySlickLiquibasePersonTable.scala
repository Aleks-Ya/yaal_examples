package controllers

import slick.jdbc.PostgresProfile.api._
import slick.lifted.Tag

class PlaySlickLiquibasePersonTable(tag: Tag) extends Table[PlaySlickLiquibasePerson](tag, "PERSONS") {
  def id = column[Int]("ID", O.PrimaryKey)

  def name = column[String]("NAME")

  def * = (id, name).mapTo[PlaySlickLiquibasePerson]
}

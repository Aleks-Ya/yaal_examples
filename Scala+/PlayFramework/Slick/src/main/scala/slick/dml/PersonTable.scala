package slick.dml

import slick.jdbc.PostgresProfile.api._
import slick.lifted.Tag
class PersonTable(tag: Tag) extends Table[Person](tag, "PERSON_LIST") {
  def id = column[Int]("ID", O.PrimaryKey)

  def name = column[String]("NAME")

  def age = column[Int]("AGE")

  def * = (id, name, age).mapTo[Person]
}

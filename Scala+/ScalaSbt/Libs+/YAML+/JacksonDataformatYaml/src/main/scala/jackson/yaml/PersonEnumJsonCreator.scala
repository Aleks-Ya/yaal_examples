package jackson.yaml

import com.fasterxml.jackson.annotation.JsonCreator

object GenderJsonCreator extends Enumeration {
  type Gender = Value
  //noinspection ScalaUnusedSymbol
  val Male, Female = Value

  @JsonCreator
  def fromString(name: String): Gender = GenderJsonCreator.withName(name)
}

case class PersonEnumJsonCreator(name: String, gender: GenderJsonCreator.Gender)

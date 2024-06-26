package jackson.yaml

import com.fasterxml.jackson.core.`type`.TypeReference
import com.fasterxml.jackson.module.scala.JsonScalaEnumeration

class GenderType extends TypeReference[GenderTypeReference.type]

object GenderTypeReference extends Enumeration {
  type Gender = Value
  //noinspection ScalaUnusedSymbol
  val Male, Female = Value
}

case class PersonWithEnumTypeReference(name: String,
                                       @JsonScalaEnumeration(classOf[GenderType]) gender: GenderTypeReference.Gender)

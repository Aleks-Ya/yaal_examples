package artist


class Artist(val id: String, val name: String, val aliases: List[Alias]) extends Serializable {

  override def toString = s"Artist($id, $name, $aliases)"
}

class Alias(val id: String, val name: String) extends Serializable {

  override def toString = s"Alias($id, $name)"
}

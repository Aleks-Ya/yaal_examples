package spray

case class City(name: String, establishYear: Int) {
  override def hashCode(): Int = super.hashCode()

  override def equals(other: Any): Boolean = {
    if (!other.isInstanceOf[City]) {
      return false
    }
    val otherCity = other.asInstanceOf[City]
    if (otherCity.name == null || !otherCity.name.equals(name)) {
      return false
    }
    if (!otherCity.establishYear.equals(establishYear)) {
      return false
    }
    true
  }
}

object City {
  val moscowCity: City = City("Moscow", 1147)
  val moscowJson =  """{"name":"Moscow","establishYear":1147}"""
}

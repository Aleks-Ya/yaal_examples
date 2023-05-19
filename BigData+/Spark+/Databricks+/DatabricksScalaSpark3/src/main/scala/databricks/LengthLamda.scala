package databricks

object LengthLamda {
  def stringToLength(str: String): Long = {
    val l = str.length
    println(s"Length: $l")
    l
  }
}

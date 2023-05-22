package databricks.cluster.shorttrem

object ShortTermLengthLamda {
  def stringToLength(str: String): Long = {
    val l = str.length
    println(s"String length: $l")
    l
  }
}

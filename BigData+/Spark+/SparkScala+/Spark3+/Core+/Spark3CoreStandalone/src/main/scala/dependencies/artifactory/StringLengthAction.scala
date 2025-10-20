package dependencies.artifactory

import org.apache.spark.SparkContext

class StringLengthAction(sc: SparkContext) {
  def calcLength(words: Seq[String]): Int = {
    println("Starting word count calculation...")
    println(s"Use the dependency from Artifactory: ${Class.forName("hello.Main")}")
    val wordCount = sc.parallelize(words).map(_.length).sum().toInt
    println(s"Word count calculation is done: word count = $wordCount")
    wordCount
  }
}

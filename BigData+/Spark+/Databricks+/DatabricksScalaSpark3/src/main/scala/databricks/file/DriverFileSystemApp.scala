package databricks.file

import databricks.{Print, WordCount}
import org.apache.spark.SparkContext

import java.nio.file.Files

object DriverFileSystemApp {
  def main(args: Array[String]): Unit = {
    Print.printInfo(this, args)

    val tmpPath = Files.createTempFile(getClass.getName, ".tmp")
    println(s"Path: $tmpPath")
    val exists = Files.exists(tmpPath)
    println(s"Path exists: $exists")
    assert(exists)
    val expContent = "Hello\nWorld"
    println(s"Expected content: '$expContent'")
    Files.write(tmpPath, expContent.getBytes)
    val actContent = new String(Files.readAllBytes(tmpPath))
    println(s"Actual content: '$actContent'")
    assert(actContent.equals(expContent))

    val fileUri = tmpPath.toUri.toString
    println(s"File URI: $fileUri")
    val wordsRdd = SparkContext.getOrCreate().textFile(fileUri)
    WordCount.countWords(wordsRdd)

    Print.finished()
  }
}

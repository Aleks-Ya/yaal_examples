package databricks.file

import com.databricks.dbutils_v1.DBUtilsHolder.dbutils
import databricks.{Print, WordCount}
import org.apache.spark.SparkContext

import java.util.UUID

object DbfsApp {
  def main(args: Array[String]): Unit = {
    Print.printInfo(this, args)

    val tmpDir = createTempDbfsDir
    val expContent = "Hello, World!"
    val file = putDbfsFile(tmpDir, expContent)
    val actContent = readDbfsFile(file)
    assert(actContent.equals(expContent))

    val wordsRdd = SparkContext.getOrCreate().textFile(file)
    WordCount.countWords(wordsRdd)

    Print.finished()
  }

  private def readDbfsFile(file: String) = {
    val actContent = dbutils.fs.head(file, 100)
    println(s"Actual content: $actContent")
    actContent
  }

  private def putDbfsFile(tmpDir: String, expContent: String) = {
    val file = s"$tmpDir/data.txt"
    println(s"File path: $file")
    println(s"Expected content: $expContent")
    val putResult = dbutils.fs.put(file, expContent)
    assert(putResult)
    file
  }

  private def createTempDbfsDir = {
    val tmpDir = s"dbfs:/tmp/DbfsApp/${UUID.randomUUID()}"
    println(s"Temp dir: $tmpDir")
    val mkdirsResult = dbutils.fs.mkdirs(tmpDir)
    assert(mkdirsResult)
    tmpDir
  }
}

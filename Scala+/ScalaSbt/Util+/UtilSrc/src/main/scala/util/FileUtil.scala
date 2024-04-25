package util

import java.io.{File, FileWriter}
import java.nio.file.{Files, Path}
import scala.io.Source

object FileUtil {
  def write(file: File, content: String): Unit = {
    var writer: FileWriter = null
    try {
      writer = new FileWriter(file)
      writer.write(content)
    } finally {
      if (writer != null) {
        writer.close()
      }
    }
  }

  def read(file: File): String = {
    val source = Source.fromFile(file)
    val content = source.getLines.mkString("\n")
    source.close()
    content
  }

  def createAbsentTmpDirStr(): String = createAbsentTmpDirPath().toString

  def createAbsentTmpDirPath(): Path = {
    val dir = Files.createTempDirectory(FileUtil.getClass.getSimpleName.replace("$", "") + "_")
    Files.delete(dir)
    println(s"Absent temp directory was created: $dir")
    dir
  }
}

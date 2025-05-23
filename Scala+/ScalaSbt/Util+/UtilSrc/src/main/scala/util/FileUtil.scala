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

  def write(path: Path, content: String): Unit = write(path.toFile, content)

  def writeNewTmpFile(content: String): Path = {
    val file = createTmpFile()
    write(file, content)
    file
  }

  def read(file: File): String = {
    val source = Source.fromFile(file)
    val content = source.getLines.mkString("\n")
    source.close()
    content
  }

  def read(path: Path): String = read(path.toFile)

  def createExistingTmpDirPath(): Path = {
    val dir = Files.createTempDirectory(FileUtil.getClass.getSimpleName.replace("$", "") + "_")
    if (Files.notExists(dir)) throw new IllegalStateException()
    println(s"Existing temp directory was created: $dir")
    dir
  }

  def createExistingTmpDirStr(): String = createExistingTmpDirPath().toString

  def createAbsentTmpDirStr(): String = createAbsentTmpDirPath().toString

  def createAbsentTmpDirPath(): Path = {
    val dir = createExistingTmpDirPath()
    Files.delete(dir)
    if (Files.exists(dir)) throw new IllegalStateException()
    println(s"Absent temp directory was created: $dir")
    dir
  }

  def createTmpFile(): Path = Files.createTempFile(FileUtil.getClass.getSimpleName.replace("$", ""), ".tmp")
}

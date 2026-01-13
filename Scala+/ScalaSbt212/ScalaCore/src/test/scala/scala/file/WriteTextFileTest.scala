package scala.file

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import java.nio.file.Files
import scala.io.Source
import scala.reflect.io.File

class WriteTextFileTest extends AnyFlatSpec with Matchers {
  private val prefix = classOf[WriteTextFileTest].getSimpleName
  private val suffix = ".tmp"

  it should "scala.reflect.io.File#writeAll()" in {
    val expContent = "abc"

    val javaFile = Files.createTempFile(prefix, suffix).toFile
    val scalaFile = new File(javaFile)
    scalaFile.writeAll(expContent)

    val source = Source.fromFile(javaFile)
    val actContent = source.getLines.mkString("\n")
    source.close()
    actContent shouldEqual expContent
  }

}

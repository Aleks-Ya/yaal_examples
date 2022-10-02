package core.files

import core.Factory
import org.apache.spark.SparkFiles
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import java.nio.charset.Charset
import java.nio.file.{Files, Paths}

class SparkFilesTest extends AnyFlatSpec with Matchers {
  private val prefix = classOf[SparkFilesTest].getSimpleName
  private val suffix = ".tmp"

  it should "use a Int variable" in {
    val fileContent = "abc"
    val path = Files.createTempFile(prefix, suffix)
    Files.write(path, fileContent.getBytes(Charset.defaultCharset()))
    val filename = path.getFileName.toString
    Factory.sc.addFile(path.toString)
    val actContent = Factory.sc.parallelize(Array(1, 2, 3))
      .map(x => x + Files.readString(Paths.get(SparkFiles.get(filename))))
      .reduce((s1, s2) => s1 + s2)
    actContent shouldEqual "1abc2abc3abc"
  }
}

package json4s.create

import org.json4s.{JLong, JsonWriter}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import java.io.StringWriter

class JsonWriterTest extends AnyFlatSpec with Matchers {
  it should "create a JSON string" in {
    val writer = JsonWriter.streaming(new StringWriter, alwaysEscapeUnicode = true)
    writer.addJValue(JLong(42))
    writer.result.toString shouldEqual "42"
  }
}
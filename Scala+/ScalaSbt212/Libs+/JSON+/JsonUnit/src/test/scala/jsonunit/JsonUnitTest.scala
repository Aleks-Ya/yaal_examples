package jsonunit

import net.javacrumbs.jsonunit.JsonAssert.assertJsonEquals
import org.scalatest.flatspec.AnyFlatSpec

class JsonUnitTest extends AnyFlatSpec {

  it should "compare JSONs" in {
    assertJsonEquals("""{"test":1}""", """{"test": 1}""")
  }

}
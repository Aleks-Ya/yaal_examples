package jackson.yaml

import com.fasterxml.jackson.databind.json.JsonMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class SerializeToYamlTest extends AnyFlatSpec with Matchers {
  it should "serialize a Scala object" in {
    val mapper = JsonMapper.builder(new YAMLFactory()).addModule(DefaultScalaModule).build()
    val obj = Seq("a", "b", "c")
    val yaml = mapper.writeValueAsString(obj)
    yaml shouldEqual
      """---
        |- "a"
        |- "b"
        |- "c"
        |""".stripMargin
  }
}
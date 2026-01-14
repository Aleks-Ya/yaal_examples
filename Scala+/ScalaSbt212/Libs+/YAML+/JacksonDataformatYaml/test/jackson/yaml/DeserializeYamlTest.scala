package jackson.yaml

import com.fasterxml.jackson.databind.json.JsonMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class DeserializeYamlTest extends AnyFlatSpec with Matchers {
  private val mapper = JsonMapper.builder(new YAMLFactory()).addModule(DefaultScalaModule).build()

  it should "deserialize YAML to Scala object" in {
    val yaml =
      """---
        |- "a"
        |- "b"
        |- "c"
        |""".stripMargin
    val obj = mapper.readValue(yaml, classOf[Seq[String]])
    obj shouldEqual Seq("a", "b", "c")
  }

  it should "deserialize YAML to POJO" in {
    val yaml =
      """---
        |name: John
        |age: 30
        |location:
        |  city: London
        |  available: true
        |""".stripMargin
    val obj = mapper.readValue(yaml, classOf[Person])
    obj shouldEqual Person("John", 30, Location("London", available = true))
  }

  it should "deserialize YAML to Map" in {
    val yaml =
      """---
        |name: John
        |age: 30
        |location:
        |  city: London
        |  available: true
        |""".stripMargin
    val map = mapper.readValue(yaml, classOf[Map[String, Any]])
    map shouldEqual Map("name" -> "John", "age" -> 30, "location" -> Map("city" -> "London", "available" -> true))
  }

  it should "deserialize a typed alias" in {
    val yaml =
      """---
        |name: John
        |age: 30
        |""".stripMargin
    val obj = mapper.readValue(yaml, classOf[PersonTyped])
    obj shouldEqual PersonTyped("John", 30)
  }

}
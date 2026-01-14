package jackson.yaml

import com.fasterxml.jackson.databind.json.JsonMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class DeserializeEnumTest extends AnyFlatSpec with Matchers {
  private val mapper = JsonMapper.builder(new YAMLFactory()).addModule(DefaultScalaModule).build()

  it should "deserialize an enum using JsonCreator" in {
    val yaml =
      """---
        |name: John
        |gender: Male
        |""".stripMargin
    val obj = mapper.readValue(yaml, classOf[PersonEnumJsonCreator])
    obj shouldEqual PersonEnumJsonCreator("John", GenderJsonCreator.Male)
  }

  it should "deserialize an enum using TypeReference" in {
    val yaml =
      """---
        |name: John
        |gender: Male
        |""".stripMargin
    val obj = mapper.readValue(yaml, classOf[PersonWithEnumTypeReference])
    obj shouldEqual PersonWithEnumTypeReference("John", GenderTypeReference.Male)
  }

}
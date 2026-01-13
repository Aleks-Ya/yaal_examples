package scala.xml

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class DeserializeXmlTest extends AnyFlatSpec with Matchers {

  it should "parse XML from a string" in {
    val text = """<person name="John" age="30"><info>abcd</info></person>"""
    val xml = XML.loadString(text)
    val name = xml \\ "person" \\ "@name"
    val age = xml \\ "person" \\ "@age"
    val info = xml \\ "person" \\ "info"
    name.text shouldEqual "John"
    age.text.toInt shouldEqual 30
    info.text shouldEqual "abcd"
  }

  it should "parse XML variable from literal" in {
    val xml =
      <person name="John" age="30">
        <info>abcd</info>
      </person>
    val name = xml \\ "person" \\ "@name"
    val age = xml \\ "person" \\ "@age"
    val info = xml \\ "person" \\ "info"
    name.text shouldEqual "John"
    age.text.toInt shouldEqual 30
    info.text shouldEqual "abcd"
  }

  it should "parse XML to an object" in {
    val xml =
      <person name="John" age="30">
        <info>abcd</info>
      </person>

    class Person() {
      var name: String = ""
      var age: Int = 0

      def this(name: String, age: Int) = {
        this()
        this.name = name
        this.age = age
      }
    }

    val name = xml \\ "person" \\ "@name"
    val age = xml \\ "person" \\ "@age"
    val person = new Person(name.text, age.text.toInt)
    person.name shouldEqual "John"
    person.age shouldEqual 30
  }

}

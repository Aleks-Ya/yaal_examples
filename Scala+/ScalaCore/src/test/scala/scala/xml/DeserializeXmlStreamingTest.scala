package scala.xml

import org.scalatest.{FlatSpec, Matchers}

import scala.xml.pull._

class DeserializeXmlStreamingTest extends FlatSpec with Matchers {

  it should "stream parse" in {
    val src = scala.io.Source.fromString("""<hello name="John"><world/></hello>""")
    val er = new XMLEventReader(src)
    while (er.hasNext) {
      val event = er.next
      println(event)
    }
  }
}

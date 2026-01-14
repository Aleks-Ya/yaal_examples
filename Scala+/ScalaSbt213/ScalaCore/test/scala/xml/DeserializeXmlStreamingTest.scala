package scala.xml

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import java.io.StringReader
import javax.xml.stream.XMLInputFactory

class DeserializeXmlStreamingTest extends AnyFlatSpec with Matchers {

  it should "stream parse" in {
    val src = new StringReader("""<hello name="John"><world/></hello>""")
    val er = XMLInputFactory.newFactory().createXMLEventReader(src)
    while (er.hasNext) {
      val event = er.next
      println(event)
    }
  }
}

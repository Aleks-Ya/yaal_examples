package dom

import groovy.xml.DOMBuilder
import groovy.xml.dom.DOMCategory
import org.junit.jupiter.api.Test
import static org.junit.jupiter.api.Assertions.assertEquals

/**
 * Чтение и парсинг XML из файла  с помощью DOMBuilder.
 */
class ReadXmlFile {

    @Test
    void parse() {
        def reader = new InputStreamReader(ReadXmlFile.class.getResourceAsStream('plan.xml'))
        def doc = DOMBuilder.parse(reader)
        def plan = doc.documentElement
        use(DOMCategory) {
            assertEquals('plan', plan.nodeName)
            assertEquals('week', plan[1].nodeName)
            assertEquals(2, plan.week.size())
            assertEquals('week', plan.week[0].nodeName)
            assertEquals('8', plan[1].'@capacity')
        }
    }
}
package dom

import groovy.xml.DOMBuilder
import groovy.xml.dom.DOMCategory
import org.junit.Test

/**
 * Поиск узлов XML-документа по условиям.
 */
class SearchNodes {
    @Test
    public void byNodeName() {
        def reader = new InputStreamReader(SearchNodes.class.getResourceAsStream('plan.xml'))
        def doc = DOMBuilder.parse(reader)
        def plan = doc.documentElement
        use(DOMCategory) {
            println plan.childNodes.findAll { it.name() == 'week' }
        }
    }
}
import groovy.xml.DOMBuilder
import groovy.xml.dom.DOMCategory
import org.junit.Test

/**
 * Изменение XML документа в памяти.
 */
class ModifyXml {

    @Test
    void appendNode() {
        def reader = new InputStreamReader(ModifyXml.class.getResourceAsStream('plan.xml'))
        def doc = DOMBuilder.parse(reader)
        def plan = doc.documentElement
        use(DOMCategory) {
            plan.appendNode('itemName', "Item value.")
        }
        println plan
    }
}
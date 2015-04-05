package dom

import groovy.xml.DOMBuilder
import groovy.xml.dom.DOMCategory
import org.custommonkey.xmlunit.DetailedDiff
import org.custommonkey.xmlunit.Diff
import org.custommonkey.xmlunit.XMLUnit
import org.junit.Test

import static org.junit.Assert.assertTrue

/**
 * Изменение XML документа в памяти.
 */
class ModifyXml {

    @Test
    void appendNode() {
        def actualReader = new StringReader(
                """
            <plan>
                <week capacity="8" />
            </plan>
            """
        )

        def actualDoc = DOMBuilder.parse(actualReader)
        def plan = actualDoc.documentElement
        use(DOMCategory) {
            plan.appendNode('itemName', "Item value.")
        }

        def expectedDoc = XMLUnit.buildTestDocument(
                """
            <plan>
                <week capacity="8" />
                <itemName>Item value.</itemName>
            </plan>
            """
        )

        XMLUnit.setIgnoreWhitespace(true)

        DetailedDiff diff = new DetailedDiff(new Diff(expectedDoc, actualDoc))
        assertTrue(diff.toString(), diff.similar())
        println plan
    }
}
package xmlparser

import org.junit.Test

import static org.junit.Assert.assertEquals

/**
 * Изменение узлов XML-документа.
 */
class Modify {
    def plan = new XmlParser().parse(ReadXmlFile.class.getResourceAsStream('plan.xml'))
    def xmlPrinter = new XmlNodePrinter()

    /**
     * Изменение содержимого узла (value).
     */
    @Test
    public void changeValue() {
        def found = plan.week[0].task[1]
        println "Old: ${found.value()}"
        def s = 'New value'
        found.value = s
        println "New: ${found.value()}"
        assertEquals(s, found.value())
    }

    /**
     * Изменение значения атрибута узла.
     */
    @Test
    public void changeAttribute() {
        def found = plan.week[0].task[1]
        println "Old: ${found.@done}"
        assertEquals('3', found.@done)
        def s = '777'
        found.@done = s
        println "New: ${found.@done}"
        assertEquals(s, found.@done)

        xmlPrinter.print(found)
        xmlPrinter.print(plan)
    }

    /**
     * Добавление узла в XML.
     */
    @Test
    public void addNode() {
        assertEquals(2, plan.week.size())
        plan.appendNode('week', [first: 'today', second: 'tomorrow'])
        xmlPrinter.print(plan)
        assertEquals(3, plan.week.size())
    }

}
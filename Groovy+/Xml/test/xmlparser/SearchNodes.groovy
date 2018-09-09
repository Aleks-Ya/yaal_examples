package xmlparser

import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import static org.junit.jupiter.api.Assertions.assertEquals

/**
 * Поиск узлов XML-документа по условиям.
 */
class SearchNodes {
    def plan = new XmlParser().parse(ReadXmlFile.class.getResourceAsStream('plan.xml'))

    @Test
    void byNodeName() {
        def found = plan.findAll { 'week' == it.name() }
        found.each { println it }
        assertEquals(2, found.size())
    }

    @Test
    void byAttribute() {
        def found = plan.findAll { '8' == it.@capacity }
        found.each { println it }
        assertEquals(2, found.size())
    }

    @Test
    void byNodeNameAndAttribute() {
        def found = plan.week.findAll { '8' == it.@capacity }
        found.each { println it }
        assertEquals(2, found.size())
    }

    /**
     * Поиск по всей иерархии узлов.
     */
    @Test
    @Disabled("НЕ РАБОТАЕТ: ищет только по детям")
    void allHierarchy() {
        def found = plan.findAll { 'task'.equals(it.name()) }
        found.each { println it }
        assertEquals(5, found.size())
    }
}
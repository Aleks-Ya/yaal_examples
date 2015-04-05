package xmlparser

import org.junit.Ignore
import org.junit.Test

import static org.junit.Assert.assertEquals

/**
 * Поиск узлов XML-документа по условиям.
 */
class SearchNodes {
    def plan = new XmlParser().parse(ReadXmlFile.class.getResourceAsStream('plan.xml'))

    @Test
    public void byNodeName() {
        def found = plan.findAll { 'week' == it.name() }
        found.each { println it }
        assertEquals(2, found.size())
    }

    @Test
    public void byAttribute() {
        def found = plan.findAll { '8' == it.@capacity }
        found.each { println it }
        assertEquals(2, found.size())
    }

    @Test
    public void byNodeNameAndAttribute() {
        def found = plan.week.findAll { '8' == it.@capacity }
        found.each { println it }
        assertEquals(2, found.size())
    }

    /**
     * Поиск по всей иерархии узлов.
     */
    @Test
    @Ignore("НЕ РАБОТАЕТ: ищет только по детям")
    public void allHierarchy() {
        def found = plan.findAll { 'task'.equals(it.name()) }
        found.each { println it }
        assertEquals(5, found.size())
    }
}
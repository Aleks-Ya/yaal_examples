package xmlparser

import org.junit.Test

import static org.junit.Assert.assertEquals

/**
 * Чтение и парсинг XML из файла с помощью XmlParser.
 */
class ReadXmlFile {

    @Test
    void parse() {
        def file = ReadXmlFile.class.getResourceAsStream('plan.xml')
        def plan = new XmlParser().parse(file)
        assertEquals('plan', plan.name())
        assertEquals('week', plan.week[0].name())
        assertEquals(2, plan.week.size())
        assertEquals('8', plan.week[1].'@capacity')
    }
}
package xmlparser

import org.junit.Test

import java.nio.file.Files

import static org.junit.Assert.assertFalse
import static org.junit.Assert.assertTrue

/**
 * Вывод отформатированного XML на консоль и в файл.
 */
class XmlPrinter {
    private def plan = new XmlParser().parse(ReadXmlFile.class.getResourceAsStream('plan.xml'))

    /**
     * На консоль.
     */
    @Test
    public void console() {
        def xmlPrinter = new XmlNodePrinter()
        xmlPrinter.print(plan)
    }

    /**
     * В файл.
     */
    @Test
    public void file() {
        File file = Files.createTempFile("XmlPrinter", ".tmp").toFile()
        file.deleteOnExit()
        println "'${file.text}'"
        assertTrue(file.text.isEmpty())
        PrintWriter writer = new PrintWriter(file)
        def xmlPrinter = new XmlNodePrinter(writer)
        xmlPrinter.print(plan)
        println "'${file.text}'"
        assertFalse(file.text.isEmpty())
    }
}
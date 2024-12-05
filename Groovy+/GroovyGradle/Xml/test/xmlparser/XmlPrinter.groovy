package xmlparser

import org.junit.jupiter.api.Test

import java.nio.file.Files

import static org.junit.jupiter.api.Assertions.assertFalse
import static org.junit.jupiter.api.Assertions.assertTrue

/**
 * Вывод отформатированного XML на консоль и в файл.
 */
class XmlPrinter {
    private def plan = new XmlParser().parse(XmlPrinter.class.getResourceAsStream('plan.xml'))

    /**
     * На консоль.
     */
    @Test
    void console() {
        def xmlPrinter = new XmlNodePrinter()
        xmlPrinter.print(plan)
    }

    /**
     * В файл.
     */
    @Test
    void file() {
        def file = Files.createTempFile("XmlPrinter", ".tmp").toFile()
        file.deleteOnExit()
        println "'${file.text}'"
        assertTrue(file.text.isEmpty())

        def writer = new PrintWriter(file)
        def xmlPrinter = new XmlNodePrinter(writer)
        xmlPrinter.print(plan)

        println "'${file.text}'"
        assertFalse(file.text.isEmpty())
    }
}
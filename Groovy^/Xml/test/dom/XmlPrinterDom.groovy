package dom

import groovy.xml.DOMBuilder
import org.junit.Before
import org.junit.Test
import org.w3c.dom.Element

import java.nio.file.Files

/**
 * Вывод отформатированного XML на консоль и в файл.
 */
class XmlPrinterDom {
    private Element plan
    private def doc

    @Before
    public void setUp() throws Exception {
        def reader = new InputStreamReader(xmlparser.ReadXmlFile.class.getResourceAsStream('plan.xml'))
        doc = DOMBuilder.parse(reader)
        plan = doc.documentElement
    }

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
     * НЕ ПИШЕТ В ФАЙЛ - СУКА! ВМЕСТО ФАЙЛА ПЕЧАТАЕТ В КОНСОЛЬ
     */
    @Test
    public void file() {
        File file = Files.createTempFile("XmlPrinterDom", ".tmp").toFile()
//        file.deleteOnExit()
//        println "Old: '${file.text}'"
//        assertTrue(file.text.isEmpty())
//        PrintWriter writer = new PrintWriter(file)
//        Writer writer = new FileWriter(file)
//        IndentPrinter printer = new IndentPrinter(writer)
        def sw = new StringWriter()

        new XmlNodePrinter(new PrintWriter(sw)).print(plan)

        def modifiedXml = sw.toString()
        file.write(modifiedXml)
//        def xmlPrinter = new XmlNodePrinter(writer)
//        xmlPrinter.print(plan)
//        new XmlNodePrinter(printer).print(plan)
//        writer.flush();
//        writer.close();
//        file.write(plan.toString())
//        println "New: '${new File(file.absolutePath).text}'"
//        println file.size()
//        assertFalse(file.text.isEmpty())
    }
}
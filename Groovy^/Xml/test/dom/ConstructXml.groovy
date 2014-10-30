package dom

import groovy.xml.MarkupBuilder
import org.junit.Test

/**
 * Конструирование нового XML-документа.
 */
class ConstructXml {

    @Test
    void construct() {
        def writer = new StringWriter()
        def xml = new MarkupBuilder(writer)
        xml.bibliography {
            author('William Shakespeare')
            play {
                year('1595')
                title('A Midsummer-Night')
            }
        }

        println writer
    }
}
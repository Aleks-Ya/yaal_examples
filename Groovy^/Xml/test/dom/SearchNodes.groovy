package dom

import groovy.xml.DOMBuilder
import groovy.xml.dom.DOMCategory
import org.junit.Test

/**
 * Поиск узлов XML-документа по условиям.
 */
class SearchNodes {
    @Test
    public void byNodeName() {
        def reader = new InputStreamReader(ReadXmlFile.class.getResourceAsStream('plan.xml'))
        def doc = DOMBuilder.parse(reader)
        def plan = doc.documentElement

        //Не работает нихуя!
//        println plan
//        println plan.find{
//            println it.nodeName
//            'plan'.equals(it.nodeName)}
//        println plan.childNodes

        use(DOMCategory) {
           println plan.findAll{}
//            {
//            'task' == it.nodeName
//                1 < 2
//            }.each {
//            }
        }
    }
}
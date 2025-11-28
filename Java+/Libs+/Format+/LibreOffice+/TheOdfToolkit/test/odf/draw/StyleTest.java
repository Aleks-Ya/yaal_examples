package odf.draw;

import org.apache.xerces.dom.ElementNSImpl;
import org.junit.jupiter.api.Test;
import org.odftoolkit.odfdom.doc.OdfGraphicsDocument;
import org.odftoolkit.odfdom.dom.element.style.StyleStyleElement;

import static util.CollectionUtil.iterableToStream;

class StyleTest {

    @Test
    void listStyles() throws Exception {
        try (var doc = OdfGraphicsDocument.newGraphicsDocument()) {
            var styles = doc.getDocumentStyles();

            System.out.println("DEFAULT STYLES:");
            var defaultStyles = styles.getDefaultStyles();
            iterableToStream(defaultStyles).map(ElementNSImpl::getLocalName).forEach(System.out::println);
            System.out.println();

            System.out.println("ALL STYLES:");
            var allStyles = styles.getAllStyles();
            iterableToStream(allStyles).map(StyleStyleElement::getStyleNameAttribute).forEach(System.out::println);
        }
    }

    @Test
    void stylesHierarchy() throws Exception {
        try (var doc = OdfGraphicsDocument.newGraphicsDocument()) {
            var styles = doc.getDocumentStyles();

            var allStyles = styles.getAllStyles();
            iterableToStream(allStyles)
                    .forEach(odfStyle -> {
                        var name = odfStyle.getStyleNameAttribute();
                        var parent = odfStyle.getParentStyle();
                        var children = odfStyle.getChildNodes();
                        System.out.println("Name: " + name);
                        System.out.println("Parent: " + parent);
                        System.out.println("Children: " + children);
                        System.out.println();
                    });
        }
    }
}
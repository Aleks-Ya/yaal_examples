package odf.draw;

import org.junit.jupiter.api.Test;
import org.odftoolkit.odfdom.doc.OdfGraphicsDocument;
import org.odftoolkit.odfdom.dom.element.draw.DrawPageElement;
import util.FileUtil;

class PageTest {

    @Test
    void createPage() throws Exception {
        try (var doc = OdfGraphicsDocument.newGraphicsDocument()) {
            var contentRoot = doc.getContentRoot();
            var page = contentRoot.newDrawPageElement("page");
            page.setDrawNameAttribute("First page");
            var rectElement = page.newDrawRectElement();
            rectElement.setSvgWidthAttribute("5cm");
            rectElement.setSvgHeightAttribute("5cm");

            var file = FileUtil.createAbsentTempFile(".odg");
            System.out.println("Output file: " + file.getAbsolutePath());
            doc.save(file);
        }
    }

    @Test
    void modifyDefaultPage() throws Exception {
        try (var doc = OdfGraphicsDocument.newGraphicsDocument()) {
            var contentRoot = doc.getContentRoot();
            var page = (DrawPageElement) contentRoot.getFirstChildElement();
            page.setDrawNameAttribute("Default page");
            var rectElement = page.newDrawRectElement();
            rectElement.setSvgWidthAttribute("5cm");
            rectElement.setSvgHeightAttribute("5cm");

            var file = FileUtil.createAbsentTempFile(".odg");
            System.out.println("Output file: " + file.getAbsolutePath());
            doc.save(file);
        }
    }
}
package itext.my.table;

import com.itextpdf.io.font.FontConstants;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Table;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

/**
 * not finished
 */
class TableTest {

    @Test
    void name() throws IOException {
        var file = File.createTempFile(TableTest.class.getSimpleName() + "_", ".pdf");
        var writer = new PdfWriter(file);
        var pdf = new PdfDocument(writer);
        var document = new Document(pdf, PageSize.A4.rotate());
        document.setMargins(20, 20, 20, 20);

        var font = PdfFontFactory.createFont(FontConstants.HELVETICA);
        var bold = PdfFontFactory.createFont(FontConstants.HELVETICA_BOLD);
        var table = new Table(new float[]{4, 1, 3, 4, 3, 3, 3, 3, 1});
        table.setWidthPercent(100);
//        BufferedReader br = new BufferedReader(new FileReader(DATA));
//        String line = br.readLine();
//        process(table, line, bold, true);
//        while ((line = br.readLine()) != null) {
//            process(table, line, font, false);
//        }
//        br.close();
        document.add(table);

        //Close document
        document.close();
    }
}

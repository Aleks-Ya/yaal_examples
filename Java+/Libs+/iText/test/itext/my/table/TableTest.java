package itext.my.table;

import com.itextpdf.io.font.FontConstants;
import com.itextpdf.kernel.font.PdfFont;
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
public class TableTest {

    @Test
    public void name() throws IOException {
        File file = File.createTempFile(TableTest.class.getSimpleName() + "_", ".pdf");
        PdfWriter writer = new PdfWriter(file);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf, PageSize.A4.rotate());
        document.setMargins(20, 20, 20, 20);

        PdfFont font = PdfFontFactory.createFont(FontConstants.HELVETICA);
        PdfFont bold = PdfFontFactory.createFont(FontConstants.HELVETICA_BOLD);
        Table table = new Table(new float[]{4, 1, 3, 4, 3, 3, 3, 3, 1});
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

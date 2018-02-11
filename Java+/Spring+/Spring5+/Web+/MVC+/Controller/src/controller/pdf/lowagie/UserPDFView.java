package controller.pdf.lowagie;


import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class UserPDFView extends AbstractPdfView {

    protected void buildPdfDocument(Map<String, Object> model,
                                    Document document,
                                    PdfWriter pdfWriter,
                                    HttpServletRequest request,
                                    HttpServletResponse response) throws Exception {

        @SuppressWarnings("unchecked")
        Map<String, String> userData = (Map<String, String>) model.get("userData");

        Paragraph paragraph = new Paragraph(userData.get("message"));
        document.add(paragraph);
    }
}

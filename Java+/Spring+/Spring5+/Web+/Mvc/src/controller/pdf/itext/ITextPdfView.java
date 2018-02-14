package controller.pdf.itext;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

class ITextPdfView extends AbstractITextPdfView {

    @Override
    protected void buildPdfDocument(Map<String, Object> model,
                                    Document document, PdfWriter writer, HttpServletRequest request,
                                    HttpServletResponse response) throws Exception {
        @SuppressWarnings("unchecked")
        Map<String, String> userData = (Map<String, String>) model.get("userData");

        Paragraph paragraph = new Paragraph(userData.get("message"));
        document.add(paragraph);
    }

}
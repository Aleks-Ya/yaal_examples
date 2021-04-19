package jasperreports;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

public class JasperReportsI18n {
    public static void main(String[] args) throws JRException {
        specifyFromJava();
    }

    private static void specifyFromJava() throws JRException {
        InputStream template = JasperReportsI18n.class.getResourceAsStream("JasperReportsI18n.jrxml");
        JasperDesign design = JRXmlLoader.load(template);
        JasperReport report = JasperCompileManager.compileReport(design);
        Map<String, Object> parameters = new HashMap<String, Object>();

        Locale locale = Locale.ENGLISH;
//        Locale locale = Locale.getDefault();
        ResourceBundle bundle = ResourceBundle.getBundle("ru.yaal.examples.java.jasperreports.messages", locale);
        parameters.put(JRParameter.REPORT_RESOURCE_BUNDLE, bundle);

        JasperPrint print = JasperFillManager.fillReport(report, parameters, new JREmptyDataSource());
        JasperExportManager.exportReportToHtmlFile(print, "i18n.html");
    }

    private static void specifyFromJrxml() throws JRException {
        InputStream template = JasperReportsI18n.class.getResourceAsStream("JasperReportsI18n.jrxml");
        JasperDesign design = JRXmlLoader.load(template);
        JasperReport report = JasperCompileManager.compileReport(design);
        Map<String, Object> parameters = new HashMap<String, Object>();

        Locale locale = Locale.ENGLISH;
//        Locale locale = Locale.getDefault();
        parameters.put(JRParameter.REPORT_LOCALE, locale);

        JasperPrint print = JasperFillManager.fillReport(report, parameters, new JREmptyDataSource());
        JasperExportManager.exportReportToHtmlFile(print, "i18n.html");
    }
}

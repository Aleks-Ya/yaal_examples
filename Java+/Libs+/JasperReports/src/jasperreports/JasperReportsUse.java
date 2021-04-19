package jasperreports;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRMapArrayDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class JasperReportsUse {
    public static void main(String[] args) throws JRException, IOException, SQLException {
//        emptyDataSource();
//        giveParametersFromJava();
//        giveFieldsFromMapDataSource();
//        giveFieldsFromJDBCDataSource();
//        giveFieldsFromJdbcBuildedQuery();
        giveFieldsFromHQL();
    }

    static void emptyDataSource() throws JRException, IOException {
        InputStream template = JasperReportsUse.class.getResourceAsStream("UselessReport.jrxml");
        JasperDesign design = JRXmlLoader.load(template);
        template.close();
        JasperReport report = JasperCompileManager.compileReport(design);
        JasperPrint print = JasperFillManager.fillReport(report, new HashMap<String, Object>(), new JREmptyDataSource());
        JasperExportManager.exportReportToHtmlFile(print, "report.html");
    }

    static void giveParametersFromJava() throws JRException, IOException {
        InputStream template = JasperReportsUse.class.getResourceAsStream("UselessReport.jrxml");
        JasperDesign design = JRXmlLoader.load(template);
        template.close();
        JasperReport report = JasperCompileManager.compileReport(design);
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("author", "Yablokov");
        JasperPrint print = JasperFillManager.fillReport(report, parameters, new JREmptyDataSource());
        JasperExportManager.exportReportToHtmlFile(print, "report.html");
    }

    static void giveFieldsFromMapDataSource() throws JRException, IOException {
        InputStream template = JasperReportsUse.class.getResourceAsStream("UselessReport.jrxml");
        JasperDesign design = JRXmlLoader.load(template);
        template.close();
        JasperReport report = JasperCompileManager.compileReport(design);

        Map<String, Object> data1 = new HashMap<String, Object>();
        data1.put("street", "Ленина");
        data1.put("house", "33");

        Map<String, Object> data2 = new HashMap<String, Object>();
        data2.put("street", "Широкая");
        data2.put("house", "170");

        JRDataSource source = new JRMapArrayDataSource(new Map[]{data1, data2});

        JasperPrint print = JasperFillManager.fillReport(report, new HashMap<String, Object>(), source);
        JasperExportManager.exportReportToHtmlFile(print, "report.html");
    }

    static void giveFieldsFromJDBCDataSource() throws JRException, IOException, SQLException {
        InputStream template = JasperReportsUse.class.getResourceAsStream("UselessReport.jrxml");
        JasperDesign design = JRXmlLoader.load(template);
        template.close();
        JasperReport report = JasperCompileManager.compileReport(design);

        String url = "jdbc:postgresql://localhost:5432/report_data";
        Connection connection = DriverManager.getConnection(url, "postgres", "postgres");
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM people");
        connection.close();
        JRDataSource source = new JRResultSetDataSource(resultSet);
        JasperPrint print = JasperFillManager.fillReport(report, new HashMap<String, Object>(), source);
        JasperExportManager.exportReportToHtmlFile(print, "report.html");
    }

    static void giveFieldsFromJdbcBuildedQuery() throws JRException, IOException, SQLException {
        InputStream template = JasperReportsUse.class.getResourceAsStream("UselessReport.jrxml");
        JasperDesign design = JRXmlLoader.load(template);
        template.close();
        JasperReport report = JasperCompileManager.compileReport(design);

        String url = "jdbc:postgresql://localhost:5432/report_data";
        Connection connection = DriverManager.getConnection(url, "postgres", "postgres");
        JasperPrint print = JasperFillManager.fillReport(report, new HashMap<String, Object>(), connection);
        connection.close();
        JasperExportManager.exportReportToHtmlFile(print, "report.html");
    }

    static void giveFieldsFromHQL() throws JRException, IOException, SQLException {
        InputStream template = JasperReportsUse.class.getResourceAsStream("UselessReport.jrxml");
        JasperDesign design = JRXmlLoader.load(template);
        template.close();
        JasperReport report = JasperCompileManager.compileReport(design);

        String url = "jdbc:postgresql://localhost:5432/report_data";
        Connection connection = DriverManager.getConnection(url, "postgres", "postgres");
        JasperPrint print = JasperFillManager.fillReport(report, new HashMap<String, Object>(), connection);
        connection.close();
        JasperExportManager.exportReportToHtmlFile(print, "report.html");
    }
}
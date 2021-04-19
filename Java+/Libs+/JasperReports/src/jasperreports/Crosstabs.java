package jasperreports;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;

public class Crosstabs {
    public static void main(String[] args) throws JRException, SQLException {
        long start = System.currentTimeMillis();
        InputStream template = Crosstabs.class.getResourceAsStream("CrossTabs.jrxml");
        JasperDesign design = JRXmlLoader.load(template);
        String url = "jdbc:postgresql://localhost:5432/set";
        Connection connection = DriverManager.getConnection(url, "postgres", "postgres");
//        Statement statement = connection.createStatement();
//        ResultSet resultSet = statement.executeQuery(
//                "SELECT g.name AS group_name, p.name AS product_name, price.price AS price " +
//                        "FROM un_cg_group AS g, un_cg_product AS p, un_cg_manufacturer, un_cg_price AS price " +
//                        "WHERE p.group_code = g.code " +
//                        "AND p.manufacturer_code = un_cg_manufacturer.code " +
//                        "AND price.product_marking = p.markingofthegood " +
//                        "AND price.product_status = p.status " +
//                        "ORDER BY price " +
//                        "LIMIT 100;");
//        JRDataSource source = new JRResultSetDataSource(resultSet);

        JasperReport report = JasperCompileManager.compileReport(design);
        JasperPrint print = JasperFillManager.fillReport(report, new HashMap<String, Object>(), connection);
        connection.close();
        JasperExportManager.exportReportToHtmlFile(print, "CrossTabs.html");
        long finish = System.currentTimeMillis();
        System.out.printf("Timing: %d sec.\n", (finish - start) / 1000);
    }
}

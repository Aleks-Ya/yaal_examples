package embedded;

import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Row;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.arrayWithSize;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class ScanTest {

    @Test
    public void rowPrefixFilter() throws IOException, InterruptedException {
        HBaseAdmin admin = HBaseHelper.admin;
        Connection connection = HBaseHelper.connection;

        String columnFamilyName = "CF";
        byte[] cf = columnFamilyName.getBytes();
        byte[] cq = "CQ".getBytes();

        String tableNameStr = "row_prefix_filter";
        TableName tableName = TableName.valueOf(tableNameStr);
        HTableDescriptor tableDescriptor = new HTableDescriptor(tableName);
        tableDescriptor.addFamily(new HColumnDescriptor(columnFamilyName));
        admin.createTable(tableDescriptor);
        Table table = connection.getTable(tableName);

        String rowKey1Str = "ROWKEY-1";
        String rowKey2Str = "ROWKEY-2";
        String rowKey3Str = "R-KEY";
        String rowData1Str = "DATA-1";
        String rowData2Str = "DATA-2";
        String rowData3Str = "DATA-3";

        byte[] rowKey1Bytes = Bytes.toBytes(rowKey1Str);
        byte[] rowKey2Bytes = Bytes.toBytes(rowKey2Str);
        byte[] rowKey3Bytes = Bytes.toBytes(rowKey3Str);
        byte[] rowData1Bytes = Bytes.toBytes(rowData1Str);
        byte[] rowData2Bytes = Bytes.toBytes(rowData2Str);
        byte[] rowData3Bytes = Bytes.toBytes(rowData3Str);

        Put put1 = new Put(rowKey1Bytes);
        put1.addColumn(cf, cq, rowData1Bytes);

        Put put2 = new Put(rowKey2Bytes);
        put2.addColumn(cf, cq, rowData2Bytes);

        Put put3 = new Put(rowKey3Bytes);
        put3.addColumn(cf, cq, rowData3Bytes);

        List<? extends Row> putActions = Arrays.asList(put1, put2, put3);
        Object[] putResults = new Object[putActions.size()];
        table.batch(putActions, putResults);
        assertThat(putResults, arrayWithSize(3));

        Scan scan = new Scan();
        scan.addColumn(cf, cq);
        scan.setRowPrefixFilter(Bytes.toBytes("ROWKEY"));
        List<Result> results = new ArrayList<>();
        try (ResultScanner rs = table.getScanner(scan)) {
            for (Result r = rs.next(); r != null; r = rs.next()) {
                results.add(r);
            }
        }

        assertThat(results, hasSize(2));

        Result result1 = results.get(0);
        Result result2 = results.get(1);

        assertEquals(Bytes.toString(result1.getRow()), rowKey1Str);
        assertEquals(Bytes.toString(result1.value()), rowData1Str);
        assertEquals(Bytes.toString(result2.getRow()), rowKey2Str);
        assertEquals(Bytes.toString(result2.value()), rowData2Str);
    }
}
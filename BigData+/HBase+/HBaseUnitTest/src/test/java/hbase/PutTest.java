package hbase;

import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class PutTest {

    @Test
    public void put() throws IOException {
        HBaseAdmin admin = HBaseHelper.admin;
        Connection connection = HBaseHelper.connection;

        String columnFamilyName = "CF2";
        byte[] cf = columnFamilyName.getBytes();
        byte[] cq1 = "CQ-1".getBytes();
        byte[] cq2 = "CQ-2".getBytes();

        String tableNameStr = getClass().getName();
        TableName tableName = TableName.valueOf(tableNameStr);
        HTableDescriptor tableDescriptor = new HTableDescriptor(tableName);
        tableDescriptor.addFamily(new HColumnDescriptor(columnFamilyName));
        admin.createTable(tableDescriptor);
        Table table = connection.getTable(tableName);

        String rowKeyStr = "ROWKEY-1";
        String rowData1Str = "DATA-1";
        String rowData2Str = "DATA-2";

        byte[] rowKeyBytes = Bytes.toBytes(rowKeyStr);
        byte[] rowData1Bytes = Bytes.toBytes(rowData1Str);
        byte[] rowData2Bytes = Bytes.toBytes(rowData2Str);

        Put put = new Put(rowKeyBytes);
        put.addColumn(cf, cq1, rowData1Bytes);
        put.addColumn(cf, cq2, rowData2Bytes);
        table.put(put);

        Get get1 = new Get(rowKeyBytes);
        get1.addColumn(cf, cq1);
        Result result1 = table.get(get1);
        assertEquals(Bytes.toString(result1.getRow()), rowKeyStr);
        assertEquals(Bytes.toString(result1.value()), rowData1Str);

        Get get2 = new Get(rowKeyBytes);
        get2.addColumn(cf, cq2);
        Result result2 = table.get(get2);
        assertEquals(Bytes.toString(result2.getRow()), rowKeyStr);
        assertEquals(Bytes.toString(result2.value()), rowData2Str);
    }
}
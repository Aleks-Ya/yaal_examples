package embedded;

import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Row;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.arrayWithSize;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class PutTest {

    @Test
    public void singlePut() throws IOException {
        HBaseAdmin admin = HBaseHelper.admin;
        Connection connection = HBaseHelper.connection;

        String columnFamilyName = "CF";
        byte[] cf = columnFamilyName.getBytes();
        byte[] cq1 = "CQ-1".getBytes();
        byte[] cq2 = "CQ-2".getBytes();

        String tableNameStr = "single_put_table";
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

    @Test
    public void batchPut() throws IOException, InterruptedException {
        HBaseAdmin admin = HBaseHelper.admin;
        Connection connection = HBaseHelper.connection;

        String columnFamilyName = "CF";
        byte[] cf = columnFamilyName.getBytes();
        byte[] cq1 = "CQ-1".getBytes();
        byte[] cq2 = "CQ-2".getBytes();

        String tableNameStr = "batch_put_table";
        TableName tableName = TableName.valueOf(tableNameStr);
        HTableDescriptor tableDescriptor = new HTableDescriptor(tableName);
        tableDescriptor.addFamily(new HColumnDescriptor(columnFamilyName));
        admin.createTable(tableDescriptor);
        Table table = connection.getTable(tableName);

        String rowKey1Str = "ROWKEY-1";
        String rowKey2Str = "ROWKEY-2";
        String rowData1Str = "DATA-1";
        String rowData2Str = "DATA-2";

        byte[] rowKey1Bytes = Bytes.toBytes(rowKey1Str);
        byte[] rowKey2Bytes = Bytes.toBytes(rowKey2Str);
        byte[] rowData1Bytes = Bytes.toBytes(rowData1Str);
        byte[] rowData2Bytes = Bytes.toBytes(rowData2Str);

        Put put1 = new Put(rowKey1Bytes);
        put1.addColumn(cf, cq1, rowData1Bytes);
        put1.addColumn(cf, cq2, rowData2Bytes);

        Put put2 = new Put(rowKey2Bytes);
        put2.addColumn(cf, cq1, rowData1Bytes);
        put2.addColumn(cf, cq2, rowData2Bytes);

        List<? extends Row> putActions = Arrays.asList(put1, put2);
        Object[] putResults = new Object[putActions.size()];
        table.batch(putActions, putResults);
        assertThat(putResults, arrayWithSize(2));

        Get get1 = new Get(rowKey1Bytes);
        get1.addColumn(cf, cq1);

        Get get2 = new Get(rowKey2Bytes);
        get2.addColumn(cf, cq2);

        List<? extends Row> getActions = Arrays.asList(get1, get2);
        Object[] getResults = new Object[getActions.size()];
        table.batch(getActions, getResults);
        assertThat(getResults, arrayWithSize(2));

        Result result1 = (Result) getResults[0];
        Result result2 = (Result) getResults[1];

        assertEquals(Bytes.toString(result1.getRow()), rowKey1Str);
        assertEquals(Bytes.toString(result1.value()), rowData1Str);
        assertEquals(Bytes.toString(result2.getRow()), rowKey2Str);
        assertEquals(Bytes.toString(result2.value()), rowData2Str);
    }

    @Test
    public void singleUpdate() throws IOException {
        HBaseAdmin admin = HBaseHelper.admin;
        Connection connection = HBaseHelper.connection;

        String columnFamilyName = "CF";
        byte[] cf = columnFamilyName.getBytes();
        byte[] cq = "CQ".getBytes();

        String tableNameStr = "single_update_table";
        TableName tableName = TableName.valueOf(tableNameStr);
        HTableDescriptor tableDescriptor = new HTableDescriptor(tableName);
        tableDescriptor.addFamily(new HColumnDescriptor(columnFamilyName));
        admin.createTable(tableDescriptor);
        Table table = connection.getTable(tableName);

        String rowKeyStr = "ROWKEY";
        String rowDataOld = "DATA-1";
        String rowDataNew = "DATA-2";

        byte[] rowKeyBytes = Bytes.toBytes(rowKeyStr);
        byte[] rowDataOldBytes = Bytes.toBytes(rowDataOld);
        byte[] rowDataNewBytes = Bytes.toBytes(rowDataNew);

        Put putOld = new Put(rowKeyBytes);
        putOld.addColumn(cf, cq, rowDataOldBytes);
        table.put(putOld);

        Get getOld = new Get(rowKeyBytes);
        getOld.addColumn(cf, cq);
        Result resultOld = table.get(getOld);
        assertEquals(Bytes.toString(resultOld.getRow()), rowKeyStr);
        assertEquals(Bytes.toString(resultOld.value()), rowDataOld);

        Put putNew = new Put(rowKeyBytes);
        putNew.addColumn(cf, cq, rowDataNewBytes);
        table.put(putNew);

        Get getNew = new Get(rowKeyBytes);
        getNew.addColumn(cf, cq);
        Result resultNew = table.get(getNew);
        assertEquals(Bytes.toString(resultNew.getRow()), rowKeyStr);
        assertEquals(Bytes.toString(resultNew.value()), rowDataNew);
    }
}
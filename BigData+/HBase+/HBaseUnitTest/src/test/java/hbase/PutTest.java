package hbase;

import org.apache.hadoop.hbase.HBaseTestingUtility;
import org.apache.hadoop.hbase.client.Get;
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
        byte[] cf = "CF".getBytes();
        byte[] cq1 = "CQ-1".getBytes();
        byte[] cq2 = "CQ-2".getBytes();

        HBaseTestingUtility utility = HBaseHelper.utility;
        String tableName = getClass().getName();
        Table table = utility.createTable(Bytes.toBytes(tableName), cf);
        HBaseTestObj obj = new HBaseTestObj();
        obj.setRowKey("ROWKEY-1");
        obj.setData1("DATA-1");
        obj.setData2("DATA-2");

        Put put = new Put(Bytes.toBytes(obj.getRowKey()));
        put.addColumn(Bytes.toBytes("CF"), Bytes.toBytes("CQ-1"), Bytes.toBytes(obj.getData1()));
        put.addColumn(Bytes.toBytes("CF"), Bytes.toBytes("CQ-2"), Bytes.toBytes(obj.getData2()));
        table.put(put);


        Get get1 = new Get(Bytes.toBytes(obj.getRowKey()));
        get1.addColumn(cf, cq1);
        Result result1 = table.get(get1);
        assertEquals(Bytes.toString(result1.getRow()), obj.getRowKey());
        assertEquals(Bytes.toString(result1.value()), obj.getData1());

        Get get2 = new Get(Bytes.toBytes(obj.getRowKey()));
        get2.addColumn(cf, cq2);
        Result result2 = table.get(get2);
        assertEquals(Bytes.toString(result2.getRow()), obj.getRowKey());
        assertEquals(Bytes.toString(result2.value()), obj.getData2());
    }
}
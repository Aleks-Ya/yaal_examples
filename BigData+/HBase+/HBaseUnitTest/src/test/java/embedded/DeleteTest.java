package embedded;

import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DeleteTest {

    @Test
    public void singleDelete() throws IOException {
        HBaseAdmin admin = HBaseHelper.admin;
        Connection connection = HBaseHelper.connection;

        String columnFamilyName = "CF";
        byte[] cf = columnFamilyName.getBytes();
        byte[] cq = "CQ".getBytes();

        String tableNameStr = "single_delete_table";
        TableName tableName = TableName.valueOf(tableNameStr);
        HTableDescriptor tableDescriptor = new HTableDescriptor(tableName);
        tableDescriptor.addFamily(new HColumnDescriptor(columnFamilyName));
        admin.createTable(tableDescriptor);
        Table table = connection.getTable(tableName);

        byte[] rowKeyBytes = Bytes.toBytes("ROWKEY-1");
        byte[] rowData1Bytes = Bytes.toBytes("DATA-1");

        Get get = new Get(rowKeyBytes);
        assertFalse(table.exists(get));

        Put put = new Put(rowKeyBytes);
        put.addColumn(cf, cq, rowData1Bytes);
        table.put(put);

        assertTrue(table.exists(get));

        Delete delete = new Delete(rowKeyBytes);
        table.delete(delete);

        assertFalse(table.exists(get));
    }
}
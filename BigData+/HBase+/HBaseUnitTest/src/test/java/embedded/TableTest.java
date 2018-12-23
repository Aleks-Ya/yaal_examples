package embedded;

import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.NamespaceDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class TableTest {
    private static HBaseAdmin admin = HBaseHelper.admin;

    @Test
    public void createTable() throws IOException {
        NamespaceDescriptor namespaceDescriptor = NamespaceDescriptor.create("namespace_for_create_table").build();
        admin.createNamespace(namespaceDescriptor);

        TableName tableName = TableName.valueOf(namespaceDescriptor.getName(), "my_table");
        HTableDescriptor tableDescriptor = new HTableDescriptor(tableName);
        tableDescriptor.addFamily(new HColumnDescriptor("my_column_family"));
        admin.createTable(tableDescriptor);

        assertTrue(admin.tableExists(tableName));
    }

    @Test
    public void isTableExist() throws IOException {
        TableName notExistTableName = TableName.valueOf("not_exist_namespace", "my_table");
        assertFalse(admin.tableExists(notExistTableName));
        assertFalse(admin.tableExists("not_exist_namespace:my_table"));
        assertFalse(admin.tableExists("not_exist_namespace:my_table".getBytes()));

        TableName existTableName = TableName.valueOf("embedded", "meta");
        assertTrue(admin.tableExists(existTableName));
        assertTrue(admin.tableExists("hbase:meta"));
        assertTrue(admin.tableExists("hbase:meta".getBytes()));
    }

    @Test
    public void getNamespace() throws IOException {
        String namespace = "default";
        NamespaceDescriptor namespaceDescriptor = admin.getNamespaceDescriptor(namespace);
        assertThat(namespaceDescriptor.getName(), equalTo(namespace));
    }

    @Test
    public void deleteTable() throws IOException {
        NamespaceDescriptor namespace = NamespaceDescriptor.create("namespace_for_delete_table").build();
        admin.createNamespace(namespace);

        TableName tableName = TableName.valueOf(namespace.getName(), "my_table");
        assertFalse(admin.tableExists(tableName));

        HTableDescriptor tableDescriptor = new HTableDescriptor(tableName);
        tableDescriptor.addFamily(new HColumnDescriptor("my_column_family"));
        admin.createTable(tableDescriptor);
        assertTrue(admin.tableExists(tableName));

        admin.disableTable(tableName);
        admin.deleteTable(tableName);
        assertFalse(admin.tableExists(tableName));
    }

    @Test
    public void disableTable() throws IOException {
        NamespaceDescriptor namespace = NamespaceDescriptor.create("namespace_for_disable_table").build();
        admin.createNamespace(namespace);

        TableName tableName = TableName.valueOf(namespace.getName(), "my_table");
        HTableDescriptor tableDescriptor = new HTableDescriptor(tableName);
        tableDescriptor.addFamily(new HColumnDescriptor("my_column_family"));
        admin.createTable(tableDescriptor);

        assertFalse(admin.isTableDisabled(tableName));

        admin.disableTable(tableName);
        assertTrue(admin.isTableDisabled(tableName));

        admin.enableTable(tableName);
        assertFalse(admin.isTableDisabled(tableName));
    }

}
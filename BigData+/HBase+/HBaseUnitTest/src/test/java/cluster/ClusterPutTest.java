package cluster;

import com.google.protobuf.ServiceException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HConstants;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.NamespaceDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.security.UserGroupInformation;
import org.junit.Test;

import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class ClusterPutTest {

    @Test
    public void checkHBaseAvailable() throws IOException, ServiceException {
        Configuration config = getConfig();
        HBaseAdmin.checkHBaseAvailable(config);
    }

    private static Configuration getConfig() {
        Configuration config = HBaseConfiguration.create();
        config.set(HConstants.ZOOKEEPER_QUORUM, "ecse00100be2.epam.com,ecse00100be3.epam.com,ecse00100be4.epam.com");
        config.setInt(HConstants.ZOOKEEPER_CLIENT_PORT, 2181);
        config.setBoolean(HConstants.ZOOKEEPER_USEMULTI, true);
        config.set(HConstants.ZOOKEEPER_ZNODE_PARENT, "/hbase-unsecure-3");

        UserGroupInformation ugi = UserGroupInformation.createRemoteUser("hbase");
        UserGroupInformation.setLoginUser(ugi);

        return config;
    }

    @Test
    public void authentication() throws IOException {
        Configuration config = getConfig();

        Connection connection = ConnectionFactory.createConnection(config);
        Admin admin = connection.getAdmin();

        String namespaceName = "iablokov_security";
        boolean isNamespaceExist = Stream.of(admin.listNamespaceDescriptors())
                .map(NamespaceDescriptor::getName)
                .anyMatch(namespaceName::equals);

        if (!isNamespaceExist) {
            NamespaceDescriptor namespaceDescriptor = NamespaceDescriptor.create(namespaceName).build();
            admin.createNamespace(namespaceDescriptor);
            System.out.println("Namespace is created:" + namespaceName);
        } else {
            admin.deleteNamespace(namespaceName);
            System.out.println("Namespace is deleted:" + namespaceName);
        }
    }


    @Test
    public void put() throws IOException {
        Configuration config = getConfig();
        Connection connection = ConnectionFactory.createConnection(config);
        Admin admin = connection.getAdmin();

        String namespaceName = "iablokov";
        boolean isNamespaceExist = Stream.of(admin.listNamespaceDescriptors())
                .map(NamespaceDescriptor::getName)
                .anyMatch(namespaceName::equals);

        if (!isNamespaceExist) {
            NamespaceDescriptor namespaceDescriptor = NamespaceDescriptor.create(namespaceName).build();
            admin.createNamespace(namespaceDescriptor);
            System.out.println("Namespace is created:" + namespaceName);
        }

        String tableQualifier = getClass().getSimpleName();
        TableName tableName = TableName.valueOf(namespaceName, tableQualifier);

        if (admin.tableExists(tableName)) {
            admin.disableTable(tableName);
            admin.deleteTable(tableName);
            System.out.println("Table deleted: " + tableName.getNameAsString());
        }

        byte[] cf = "CF".getBytes();
        byte[] cq1 = "CQ-1".getBytes();
        byte[] cq2 = "CQ-2".getBytes();

        HTableDescriptor tableDescriptor = new HTableDescriptor(tableName);
        tableDescriptor.addFamily(new HColumnDescriptor(cf));
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
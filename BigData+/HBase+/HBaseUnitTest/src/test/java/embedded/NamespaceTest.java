package embedded;

import org.apache.hadoop.hbase.NamespaceDescriptor;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.junit.Test;

import java.io.IOException;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class NamespaceTest {
    private static HBaseAdmin admin = HBaseHelper.admin;

    @Test
    public void createNamespace() throws IOException {
        String namespaceName = "create_namespace";
        assertFalse(isNamespaceExist(namespaceName));
        NamespaceDescriptor namespaceDescriptor = NamespaceDescriptor.create(namespaceName).build();
        admin.createNamespace(namespaceDescriptor);
        assertTrue(isNamespaceExist(namespaceName));
    }

    @Test
    public void isNamespaceExist() throws IOException {
        assertFalse(isNamespaceExist("not_exists_namespace"));
        assertTrue(isNamespaceExist("default"));
    }

    private static boolean isNamespaceExist(String namespace) throws IOException {
        return Stream.of(admin.listNamespaceDescriptors())
                .map(NamespaceDescriptor::getName)
                .anyMatch(namespace::equals);
    }

    @Test
    public void getNamespace() throws IOException {
        String namespace = "default";
        NamespaceDescriptor namespaceDescriptor = admin.getNamespaceDescriptor(namespace);
        assertThat(namespaceDescriptor.getName(), equalTo(namespace));
    }

    @Test
    public void deleteNamespace() throws IOException {
        String namespaceName = "delete_namespace";

        assertFalse(isNamespaceExist(namespaceName));

        NamespaceDescriptor namespaceDescriptor = NamespaceDescriptor.create(namespaceName).build();
        admin.createNamespace(namespaceDescriptor);
        assertTrue(isNamespaceExist(namespaceName));

        admin.deleteNamespace(namespaceName);
        assertFalse(isNamespaceExist(namespaceName));
    }
}
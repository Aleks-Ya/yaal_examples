package microstream;

import one.microstream.storage.embedded.types.EmbeddedStorage;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;
import java.util.Date;

/**
 * Source: https://manual.docs.microstream.one/data-store/getting-started
 */
class GettingStartedTest {

    @Test
    void test1() {
        // Initialize a storage manager ("the database") with purely defaults.
        var storageManager = EmbeddedStorage.start();

        // print the last loaded root instance,
        // replace it with a current version and store it
        System.out.println(storageManager.root());
        storageManager.setRoot("Hello World! @ " + new Date());
        storageManager.storeRoot();

        // shutdown storage
        storageManager.shutdown();
    }

    @Test
    void test2() {
        // Application-specific root instance
        var root = new DataRoot();

        // Initialize a storage manager ("the database") with the given directory.
        var storageManager = EmbeddedStorage.start(
                root,             // root object
                Paths.get("data") // storage directory
        );
        // Set content data to the root element, including the time to visualize
        // changes on the next execution.
        root.setContent("Hello World! @ " + new Date());

        // Store the modified root and its content.
        storageManager.storeRoot();
        storageManager.shutdown();
    }
}
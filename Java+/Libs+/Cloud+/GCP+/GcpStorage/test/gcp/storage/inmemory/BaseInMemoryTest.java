package gcp.storage.inmemory;

import com.google.cloud.storage.Storage;
import com.google.cloud.storage.contrib.nio.testing.LocalStorageHelper;

public abstract class BaseInMemoryTest {
    protected static final String BUCKET_NAME = "the_bucket";
    protected static final String OBJECT_NAME = "file.txt";
    protected static final String CONTENT = "the content";

    protected final Storage storage = LocalStorageHelper.getOptions().getService();
}

package cloud.inmemory.object;

import cloud.inmemory.BaseInMemoryTest;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DeleteObject extends BaseInMemoryTest {
    @Test
    public void deleteExisting() {
        var blobId = BlobId.of(BUCKET_NAME, OBJECT_NAME);
        var blobInfo = BlobInfo.newBuilder(blobId).build();
        var blob = storage.create(blobInfo, CONTENT.getBytes());
        assertThat(blob, notNullValue());

        assertThat(storage.get(blobId), notNullValue());

        var result = storage.delete(blobId);
        assertTrue(result);

        assertThat(storage.get(blobId), nullValue());
    }

    @Test
    public void deleteNotExisting() {
        var blobId = BlobId.of(BUCKET_NAME, OBJECT_NAME);
        assertThat(storage.get(blobId), nullValue());

        var result = storage.delete(blobId);
        assertFalse(result);

        assertThat(storage.get(blobId), nullValue());
    }
}

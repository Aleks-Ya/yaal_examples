package storage;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.hamcrest.MatcherAssert.assertThat;

public class FileStorageTest {
    private static final String SESSION_ID_1 = "session1";
    private static final String SESSION_ID_2 = "session2";
    private static final String VALUE_1 = "abcd";
    private static final String VALUE_2 = "wxyz";
    private Storage storage;

    @Before
    public void createFile() throws IOException {
        File file = File.createTempFile(FileStorageTest.class.getSimpleName(), ".txt");
        file.deleteOnExit();
        System.out.println("Temp file: " + file);
        storage = new FileStorage(file);
    }

    @Test
    void saveAuthorizationCode() {
        assertNull(storage.readAuthenticationCode(SESSION_ID_1));
        storage.saveAuthorizationCode(SESSION_ID_1, VALUE_1);
        assertThat(storage.readAuthenticationCode(SESSION_ID_1), equalTo(VALUE_1));

        assertNull(storage.readAuthenticationCode(SESSION_ID_2));
        storage.saveAuthorizationCode(SESSION_ID_2, VALUE_2);
        assertThat(storage.readAuthenticationCode(SESSION_ID_2), equalTo(VALUE_2));
        assertThat(storage.readAuthenticationCode(SESSION_ID_1), equalTo(VALUE_1));
    }

    @Test
    void saveAccessToken() {
        assertNull(storage.readAccessToken(SESSION_ID_1));
        storage.saveAccessToken(SESSION_ID_1, VALUE_1);
        assertThat(storage.readAccessToken(SESSION_ID_1), equalTo(VALUE_1));

        assertNull(storage.readAccessToken(SESSION_ID_2));
        storage.saveAccessToken(SESSION_ID_2, VALUE_2);
        assertThat(storage.readAccessToken(SESSION_ID_2), equalTo(VALUE_2));
        assertThat(storage.readAccessToken(SESSION_ID_1), equalTo(VALUE_1));
    }

    @Test
    void saveRefreshToken() {
        assertNull(storage.readRefreshToken(SESSION_ID_1));
        storage.saveRefreshToken(SESSION_ID_1, VALUE_1);
        assertThat(storage.readRefreshToken(SESSION_ID_1), equalTo(VALUE_1));

        assertNull(storage.readRefreshToken(SESSION_ID_2));
        storage.saveRefreshToken(SESSION_ID_2, VALUE_2);
        assertThat(storage.readRefreshToken(SESSION_ID_2), equalTo(VALUE_2));
        assertThat(storage.readRefreshToken(SESSION_ID_1), equalTo(VALUE_1));
    }

    @Test
    void saveAll() {
        saveAuthorizationCode();
        saveAccessToken();
        saveRefreshToken();
    }

}
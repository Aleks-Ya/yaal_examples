package hash;

import com.google.common.hash.Hashing;
import org.junit.Test;

import java.nio.charset.Charset;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Generate hash-code of a string.
 */
public class HashingTest {
    private static final String s = "abc";

    @Test
    public void sha256() {
        String hash = Hashing.sha256().hashString(s, Charset.defaultCharset()).toString();
        assertThat(hash, equalTo("ba7816bf8f01cfea414140de5dae2223b00361a396177a9cb410ff61f20015ad"));
    }

    @Test
    public void sha512() {
        String hash = Hashing.sha512().hashString(s, Charset.defaultCharset()).toString();
        assertThat(hash, equalTo("ddaf35a193617abacc417349ae20413112e6fa4e89a97ea20a9eeee64b55d39a2192992a274fc1a836ba3c23a3feebbd454d4423643ce80e2a9ac94fa54ca49f"));
    }

    @Test
    public void sha1() {
        String hash = Hashing.sha1().hashString(s, Charset.defaultCharset()).toString();
        assertThat(hash, equalTo("a9993e364706816aba3e25717850c26c9cd0d89d"));
    }

    @Test
    public void md5() {
        String hash = Hashing.md5().hashString(s, Charset.defaultCharset()).toString();
        assertThat(hash, equalTo("900150983cd24fb0d6963f7d28e17f72"));
    }

    @Test
    public void goodFastHash() {
        String hash = Hashing.goodFastHash(40).hashString(s, Charset.defaultCharset()).toString();
        System.out.println(hash); // it isn't repeatable
    }

    @Test
    public void murmur3_32() {
        String hash = Hashing.murmur3_32().hashString(s, Charset.defaultCharset()).toString();
        assertThat(hash, equalTo("fa93ddb3"));
    }

    @Test
    public void murmur3_128() {
        String hash = Hashing.murmur3_128().hashString(s, Charset.defaultCharset()).toString();
        assertThat(hash, equalTo("6778ad3f3f3f96b4522dca264174a23b"));
    }
}
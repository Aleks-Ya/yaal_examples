package hash;

import com.google.common.hash.Hashing;
import org.junit.jupiter.api.Test;

import java.nio.charset.Charset;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Generate hash-code of a string.
 */
class HashingTest {
    private static final String s = "abc";

    @Test
    void sha256() {
        var hash = Hashing.sha256().hashString(s, Charset.defaultCharset()).toString();
        assertThat(hash).isEqualTo("ba7816bf8f01cfea414140de5dae2223b00361a396177a9cb410ff61f20015ad");
    }

    @Test
    void sha512() {
        var hash = Hashing.sha512().hashString(s, Charset.defaultCharset()).toString();
        assertThat(hash).isEqualTo("ddaf35a193617abacc417349ae20413112e6fa4e89a97ea20a9eeee64b55d39a2192992a274fc1a836ba3c23a3feebbd454d4423643ce80e2a9ac94fa54ca49f");
    }

    @Test
    void sha1() {
        @SuppressWarnings("deprecation")
        var hash = Hashing.sha1().hashString(s, Charset.defaultCharset()).toString();
        assertThat(hash).isEqualTo("a9993e364706816aba3e25717850c26c9cd0d89d");
    }

    @Test
    void md5() {
        @SuppressWarnings("deprecation")
        var hash = Hashing.md5().hashString(s, Charset.defaultCharset()).toString();
        assertThat(hash).isEqualTo("900150983cd24fb0d6963f7d28e17f72");
    }

    @Test
    void goodFastHash() {
        var hash = Hashing.goodFastHash(40).hashString(s, Charset.defaultCharset()).toString();
        System.out.println(hash); // it isn't repeatable
    }

    @Test
    void murmur3_32() {
        var hash = Hashing.murmur3_32_fixed().hashString(s, Charset.defaultCharset()).toString();
        assertThat(hash).isEqualTo("fa93ddb3");
    }

    @Test
    void murmur3_128() {
        var hash = Hashing.murmur3_128().hashString(s, Charset.defaultCharset()).toString();
        assertThat(hash).isEqualTo("6778ad3f3f3f96b4522dca264174a23b");
    }
}
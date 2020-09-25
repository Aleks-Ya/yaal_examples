package cracking.code.interview.string;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class CompressStringTest {
    @Test
    public void compress() {
        assertThat(CompressString.compress("abcccdddd"), equalTo("a1b1c3d4"));
        assertThat(CompressString.compress("abcccddd"), equalTo("abcccddd"));
        assertThat(CompressString.compress(""), equalTo(""));
        assertThat(CompressString.compress("   "), equalTo(" 3"));
    }

}
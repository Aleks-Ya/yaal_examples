package java11.clazz;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class Var {

    @Test
    public void localVar() {
        var localVar = "abc";
        assertThat(localVar, equalTo("abc"));
    }
}

package security.manager;

import org.junit.jupiter.api.Test;

import java.security.Permission;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class GrantAllSecurityManagerTest {

    @Test
    public void grantAll() {
        var securityManager = new GrantAllSecurityManager();
        System.setSecurityManager(securityManager);
        System.getProperty("java.home");
        assertThat(securityManager.invocations, equalTo(1));
    }

    public static class GrantAllSecurityManager extends SecurityManager {
        public int invocations = 0;

        @Override
        public void checkPermission(Permission perm) {
            invocations++;
        }

        @Override
        public void checkPermission(Permission perm, Object context) {
            invocations++;
        }
    }

}

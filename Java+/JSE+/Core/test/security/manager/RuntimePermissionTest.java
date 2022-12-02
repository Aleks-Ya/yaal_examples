package security.manager;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.security.AccessControlException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static security.manager.SecurityManagerHelper.configureSecurityManagerPolicyFromResource;

@Disabled("need fix exception")
class RuntimePermissionTest {

    @Test
    void setSecurityManager() {
        configureSecurityManagerPolicyFromResource(getClass(), "RuntimePermissionTest_setSecurityManager.policy");
        assertThatThrownBy(() -> System.getProperty("java.home")).isInstanceOf(AccessControlException.class);
        System.setSecurityManager(null);
        System.getProperty("java.home");
    }

}

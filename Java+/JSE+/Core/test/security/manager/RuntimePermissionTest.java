package security.manager;

import org.junit.jupiter.api.Test;

import java.security.AccessControlException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static security.manager.SecurityManagerHelper.configureSecurityManagerPolicyFromResource;

public class RuntimePermissionTest {

    @Test
    public void setSecurityManager() {
        configureSecurityManagerPolicyFromResource(getClass(), "RuntimePermissionTest_setSecurityManager.policy");
        assertThrows(AccessControlException.class, () -> System.getProperty("java.home"));

        System.setSecurityManager(null);
        System.getProperty("java.home");
    }

}

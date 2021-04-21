package security.manager;

import org.junit.jupiter.api.Test;

import java.security.AccessControlException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static security.manager.SecurityManagerHelper.configureSecurityManagerPolicyFromResource;

public class ReflectionPermissionTest {
    private final String privateField = "abc";

    @Test
    public void grantSuppressAccessChecks() throws NoSuchFieldException {
        configureSecurityManagerPolicyFromResource(getClass(), "ReflectionPermissionTest_enabled.policy");
        getClass().getDeclaredField("privateField").setAccessible(true);
    }

    @Test
    public void notGrantSuppressAccessChecks() {
        configureSecurityManagerPolicyFromResource(getClass(), "ReflectionPermissionTest_disabled.policy");
        assertThrows(AccessControlException.class, () -> getClass().getDeclaredField("privateField").setAccessible(true));
    }

}

package security.manager;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.security.AccessControlException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static security.manager.SecurityManagerHelper.configureSecurityManagerPolicyFromResource;

@Disabled("need fix exception")
class ReflectionPermissionTest {
    private final String privateField = "abc";

    @Test
    void grantSuppressAccessChecks() throws NoSuchFieldException {
        configureSecurityManagerPolicyFromResource(getClass(), "ReflectionPermissionTest_enabled.policy");
        getClass().getDeclaredField("privateField").setAccessible(true);
    }

    @Test
    void notGrantSuppressAccessChecks() {
        configureSecurityManagerPolicyFromResource(getClass(), "ReflectionPermissionTest_disabled.policy");
        assertThatThrownBy(() -> getClass().getDeclaredField("privateField").setAccessible(true))
                .isInstanceOf(AccessControlException.class);
    }

}

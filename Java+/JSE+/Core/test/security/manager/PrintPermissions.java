package security.manager;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import java.security.Permission;
import java.util.LinkedHashSet;
import java.util.Set;

import static security.manager.SecurityManagerHelper.SECURITY_POLICY_PROPERTY;
import static util.ResourceUtil.resourceToPath;

public class PrintPermissions {
    private static final LogSecurityManager securityManager = new LogSecurityManager();

    @AfterAll
    public static void afterAll() {
        var size = securityManager.permissions.size();
        var permissions = String.join("\n", securityManager.permissions);
        System.out.println("Permissions (" + size + "):\n" + permissions);
    }

    @Test
    public void printCheckedPermissions() {
        var policyFile = resourceToPath(getClass(), "PrintPermissions.policy");
        System.setProperty(SECURITY_POLICY_PROPERTY, policyFile);
        System.setSecurityManager(securityManager);
    }

    public static class LogSecurityManager extends SecurityManager {
        private final Set<String> permissions = new LinkedHashSet<>();

        @Override
        public void checkPermission(Permission perm) {
            permissions.add(perm.toString());
            super.checkPermission(perm);
        }

        @Override
        public void checkPermission(Permission perm, Object context) {
            permissions.add(perm.toString());
            super.checkPermission(perm, context);
        }
    }

}
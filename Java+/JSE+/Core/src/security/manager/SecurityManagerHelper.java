package security.manager;

import static util.ResourceUtil.resourceToPath;

public class SecurityManagerHelper {
    public static final String SECURITY_POLICY_PROPERTY = "java.security.policy";

    public static void configureSecurityManagerPolicyFromResource(Class<?> clazz, String resource) {
        var policyFile = resourceToPath(clazz, resource);
        System.setProperty(SECURITY_POLICY_PROPERTY, policyFile);
        var securityManager = new SecurityManager();
        System.setSecurityManager(securityManager);
    }
}

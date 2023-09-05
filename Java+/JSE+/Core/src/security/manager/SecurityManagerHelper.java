package security.manager;

import util.ResourceUtil;

public class SecurityManagerHelper {
    public static final String SECURITY_POLICY_PROPERTY = "java.security.policy";

    public static void configureSecurityManagerPolicyFromResource(Class<?> clazz, String resource) {
        var policyFile = ResourceUtil.resourceToStrPath(clazz, resource);
        System.setProperty(SECURITY_POLICY_PROPERTY, policyFile);
        var securityManager = new SecurityManager();
        System.setSecurityManager(securityManager);
    }
}

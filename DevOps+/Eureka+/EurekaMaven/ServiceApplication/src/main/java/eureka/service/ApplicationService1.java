package eureka.service;

/**
 * Read configuration from property file #1 (eureka-client-1.properties).
 */
public class ApplicationService1 extends ApplicationServiceBase {
    public static void main(String[] args) {
        runService("eureka-client-1");
    }
}

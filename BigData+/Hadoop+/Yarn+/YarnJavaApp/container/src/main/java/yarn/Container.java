package yarn;


import com.google.common.base.Strings;

public class Container {
    private static final String PARAM_FROM_CLIENT_TO_CONTAINER_NAME = "param_from_client";

    public static void main(String[] args) {
        System.out.println(Strings.repeat("Use classes from dependency", 1));
        System.out.println("Container: Initializing");
        System.out.println("Hello World!");
        String paramFromClient = System.getenv(PARAM_FROM_CLIENT_TO_CONTAINER_NAME);
        System.out.println("Param from client: " + paramFromClient);
        System.out.println("Environment variables: \n" + System.getenv());
        System.out.println("Java properties: \n" + System.getProperties());
        System.out.println("Container: Finalizing");
    }
}

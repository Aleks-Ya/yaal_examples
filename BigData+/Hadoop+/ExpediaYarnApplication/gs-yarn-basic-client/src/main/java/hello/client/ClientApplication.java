package hello.client;

import org.apache.hadoop.yarn.api.records.ApplicationId;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.yarn.client.YarnClient;

@EnableAutoConfiguration
public class ClientApplication {

    public static void main(String[] args) {
        System.setProperty("HADOOP_USER_NAME", "root");
        ApplicationId applicationId = SpringApplication.run(ClientApplication.class, args)
                .getBean(YarnClient.class)
                .submitApplication();
        System.out.println("App ID: " + applicationId);
    }

}

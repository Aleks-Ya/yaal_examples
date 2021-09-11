package hello.client;

import org.apache.hadoop.yarn.api.records.ApplicationId;
import org.apache.hadoop.yarn.api.records.ApplicationReport;
import org.apache.hadoop.yarn.api.records.FinalApplicationStatus;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.yarn.client.YarnClient;

import java.util.concurrent.TimeUnit;

@EnableAutoConfiguration
public class ClientApplication {

    public static void main(String[] args) throws InterruptedException {
        System.setProperty("HADOOP_USER_NAME", "root");

        YarnClient yarnClient = SpringApplication.run(ClientApplication.class, args).getBean(YarnClient.class);

        ApplicationId applicationId = yarnClient.submitApplication();
        System.out.println("App ID: " + applicationId);

        ApplicationReport report = yarnClient.getApplicationReport(applicationId);
        System.out.println("YarnApplicationState: " + report.getYarnApplicationState());

        FinalApplicationStatus finalApplicationStatus;
        do {
            report = yarnClient.getApplicationReport(applicationId);
            finalApplicationStatus = report.getFinalApplicationStatus();
            System.out.println("FinalApplicationStatus: " + finalApplicationStatus);
            TimeUnit.SECONDS.sleep(5);
        } while (finalApplicationStatus == FinalApplicationStatus.UNDEFINED);
    }

}

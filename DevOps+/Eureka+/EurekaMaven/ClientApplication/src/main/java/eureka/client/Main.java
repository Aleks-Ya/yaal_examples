package eureka.client;

import com.netflix.appinfo.ApplicationInfoManager;
import com.netflix.appinfo.EurekaInstanceConfig;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.appinfo.MyDataCenterInstanceConfig;
import com.netflix.appinfo.providers.EurekaConfigBasedInstanceInfoProvider;
import com.netflix.discovery.DefaultEurekaClientConfig;
import com.netflix.discovery.DiscoveryClient;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.EurekaClientConfig;
import com.netflix.discovery.shared.Application;
import com.netflix.discovery.shared.Applications;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        EurekaInstanceConfig instanceConfig = new MyDataCenterInstanceConfig();
        InstanceInfo instanceInfo = new EurekaConfigBasedInstanceInfoProvider(instanceConfig).get();
        ApplicationInfoManager manager = new ApplicationInfoManager(instanceConfig, instanceInfo);
        EurekaClientConfig clientConfig = new DefaultEurekaClientConfig();
        EurekaClient client = new DiscoveryClient(manager, clientConfig);
        Applications applications = client.getApplications("");
        if (applications.size() == 0) {
            throw new IllegalStateException("No registered applications");
        }
        Application serviceApp = applications.getRegisteredApplications().get(0);
        List<InstanceInfo> instances = serviceApp.getInstances();
        InstanceInfo serviceInstanceInfo = instances.get(0);
        String hostname = serviceInstanceInfo.getVIPAddress();
        int port = serviceInstanceInfo.getPort();

        System.out.println("Hostname: " + hostname);
        System.out.println("Port: " + port);

        RequestSender sender = new RequestSender();
        String data = sender.readData(hostname, port);
        System.out.println("Data: " + data);
    }
}

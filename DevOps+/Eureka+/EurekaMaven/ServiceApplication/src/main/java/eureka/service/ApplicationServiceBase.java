package eureka.service;

import com.netflix.appinfo.ApplicationInfoManager;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.appinfo.MyDataCenterInstanceConfig;
import com.netflix.appinfo.providers.EurekaConfigBasedInstanceInfoProvider;
import com.netflix.discovery.DefaultEurekaClientConfig;
import com.netflix.discovery.DiscoveryClient;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.EurekaClientConfig;

abstract class ApplicationServiceBase {

    protected static void runService() {
        MyDataCenterInstanceConfig instanceConfig = new MyDataCenterInstanceConfig();
        InstanceInfo instanceInfo = new EurekaConfigBasedInstanceInfoProvider(instanceConfig).get();
        ApplicationInfoManager manager = new ApplicationInfoManager(instanceConfig, instanceInfo);
        EurekaClientConfig clientConfig = new DefaultEurekaClientConfig();
        EurekaClient client = new DiscoveryClient(manager, clientConfig);

        RequestProcessor requestProcessor = new RequestProcessor(manager, client, instanceInfo.getVIPAddress());
        requestProcessor.start();
    }

    protected static void runService(String propertyFileName) {
        System.setProperty("eureka.client.props", propertyFileName);
        runService();
    }
}

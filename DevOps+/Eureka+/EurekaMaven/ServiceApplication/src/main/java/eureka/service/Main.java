package eureka.service;

import com.netflix.appinfo.ApplicationInfoManager;
import com.netflix.appinfo.DataCenterInfo;
import com.netflix.appinfo.EurekaInstanceConfig;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.appinfo.MyDataCenterInfo;
import com.netflix.appinfo.MyDataCenterInstanceConfig;
import com.netflix.appinfo.providers.EurekaConfigBasedInstanceInfoProvider;
import com.netflix.discovery.DefaultEurekaClientConfig;
import com.netflix.discovery.DiscoveryClient;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.EurekaClientConfig;

import javax.inject.Provider;

public class Main {
    public static void main(String[] args) {
//        DataCenterInfo dataCenterInfo = new MyDataCenterInfo(DataCenterInfo.Name.MyOwn);
        MyDataCenterInstanceConfig instanceConfig = new MyDataCenterInstanceConfig();
        InstanceInfo instanceInfo = new EurekaConfigBasedInstanceInfoProvider(instanceConfig).get();
        ApplicationInfoManager manager = new ApplicationInfoManager(instanceConfig, instanceInfo);
        EurekaClientConfig clientConfig = new DefaultEurekaClientConfig();
        EurekaClient client = new DiscoveryClient(manager, clientConfig);

        RequestProcessor requestProcessor = new RequestProcessor(manager, client, instanceInfo.getVIPAddress());
        requestProcessor.start();
    }
}

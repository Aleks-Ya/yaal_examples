package balancer;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.client.DefaultServiceInstance;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.ReactiveDiscoveryClient;
import org.springframework.cloud.client.discovery.simple.reactive.SimpleReactiveDiscoveryClient;
import org.springframework.cloud.client.discovery.simple.reactive.SimpleReactiveDiscoveryProperties;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.reactive.ReactiveLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ReactorLoadBalancer;
import org.springframework.cloud.loadbalancer.core.RoundRobinLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

import static balancer.BalancerTest.SERVICE_ID;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

@SpringBootTest(webEnvironment = NONE, properties = {"loadbalancer.client.name=" + SERVICE_ID})
class BalancerTest {
    static final String SERVICE_ID = "wiremock";
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT_1 = 9561;
    private static final int SERVER_PORT_2 = 9562;

    @Autowired
    @Qualifier("wireMockServer1")
    private WireMockServer server1;

    @Autowired
    @Qualifier("wireMockServer2")
    private WireMockServer server2;

    @Autowired
    private ReactiveLoadBalancer<ServiceInstance> loadBalancer;

    @Autowired
    private RestTemplate restTemplate;

    @Test
    void balancer() {
        var expBody1 = "Hello, World! #1";
        var path = "/hello";
        var port1 = server1.port();
        configureFor(SERVER_HOST, port1);
        stubFor(get(urlEqualTo(path)).willReturn(aResponse().withBody(expBody1)));

        var expBody2 = "Hello, World! #2";
        var port2 = server2.port();
        configureFor(SERVER_HOST, port2);
        stubFor(get(urlEqualTo(path)).willReturn(aResponse().withBody(expBody2)));

        var publisher = loadBalancer.choose();
        assertThat(publisher).isNotNull();

        var url = format("http://%s%s", SERVICE_ID, path);
        var body1 = restTemplate.getForObject(url, String.class);
        var body2 = restTemplate.getForObject(url, String.class);

        System.out.println(body1);
        System.out.println(body2);
        assertThat(new String[]{body1, body2}).contains(expBody1, expBody2);
    }

    @SpringBootConfiguration
    @EnableAutoConfiguration
    static class WireMockConfig {
        @Bean(initMethod = "start", destroyMethod = "stop")
        public WireMockServer wireMockServer1() {
            return new WireMockServer(SERVER_PORT_1);
        }

        @Bean(initMethod = "start", destroyMethod = "stop")
        public WireMockServer wireMockServer2() {
            return new WireMockServer(SERVER_PORT_2);
        }

        @Bean
        ReactorLoadBalancer<ServiceInstance> randomLoadBalancer(Environment environment,
                                                                LoadBalancerClientFactory loadBalancerClientFactory) {
            String name = environment.getProperty(LoadBalancerClientFactory.PROPERTY_NAME);
            var provider = loadBalancerClientFactory
                    .getLazyProvider(name, ServiceInstanceListSupplier.class);
            return new RoundRobinLoadBalancer(provider, name);
        }

        @Bean
        public ReactiveDiscoveryClient reactiveDiscoveryClient() {
            SimpleReactiveDiscoveryProperties properties = new SimpleReactiveDiscoveryProperties();
            var instances = Map.of(SERVICE_ID, List.of(
                    new DefaultServiceInstance("wiremock1", SERVICE_ID, SERVER_HOST, SERVER_PORT_1, false),
                    new DefaultServiceInstance("wiremock2", SERVICE_ID, SERVER_HOST, SERVER_PORT_2, false)
            ));
            properties.setInstances(instances);
            return new SimpleReactiveDiscoveryClient(properties);
        }

        @Bean
        public ServiceInstanceListSupplier discoveryClientServiceInstanceListSupplier(ConfigurableApplicationContext context) {
            return ServiceInstanceListSupplier.builder()
                    .withDiscoveryClient()
                    .withHints()
                    .withCaching()
                    .build(context);
        }

        @Bean
        @LoadBalanced
        RestTemplate restTemplate() {
            return new RestTemplate();
        }
    }

}
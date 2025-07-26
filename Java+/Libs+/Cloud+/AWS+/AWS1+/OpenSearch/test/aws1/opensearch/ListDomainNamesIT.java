package aws1.opensearch;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.elasticsearch.AWSElasticsearchClientBuilder;
import com.amazonaws.services.elasticsearch.model.ListDomainNamesRequest;
import org.junit.jupiter.api.Test;

class ListDomainNamesIT {
    @Test
    void test() {
        var credentialsProvider = new ProfileCredentialsProvider();
        var client = AWSElasticsearchClientBuilder.standard()
                .withCredentials(credentialsProvider)
                .withRegion(Regions.US_EAST_1)
                .build();
        var listDomainNamesRequest = new ListDomainNamesRequest();
        var listDomainNamesResult = client.listDomainNames(listDomainNamesRequest);
        System.out.println("Domains: " + listDomainNamesResult);
    }
}

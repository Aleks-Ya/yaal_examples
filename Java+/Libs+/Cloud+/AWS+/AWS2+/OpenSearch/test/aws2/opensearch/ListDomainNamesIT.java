package aws2.opensearch;

import org.junit.jupiter.api.Test;
import software.amazon.awssdk.services.opensearch.OpenSearchClient;
import software.amazon.awssdk.services.opensearch.model.ListDomainNamesRequest;

class ListDomainNamesIT {
    @Test
    void test() {
        try (var client = OpenSearchClient.builder().build()) {
            var response = client.listDomainNames(ListDomainNamesRequest.builder().build());
            var domainInfoList = response.domainNames();
            domainInfoList.forEach(domain -> System.out.println(domain.domainName()));
        }
    }
}

package aws2.opensearch.packages;

import aws2.s3.OpenSearchHelper;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static aws2.s3.OpenSearchHelper.DOMAIN_NAME;
import static java.time.Duration.ofMinutes;
import static java.time.Duration.ofSeconds;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static software.amazon.awssdk.services.opensearch.model.DomainPackageStatus.ACTIVE;

class AssociatePackageIT {

    @Test
    void associateDissociatePackage() {
        try (var pac = OpenSearchHelper.createPackage()) {
            var packageDetails = pac.packageDetails;
            var id = packageDetails.packageID();

            System.out.println("PackageStatus before association: " + OpenSearchHelper.getPackageStatus(id));
            System.out.println("DomainPackageStatus before association: " + OpenSearchHelper.getDomainPackageStatus(id));
            var associateResponse = OpenSearchHelper.client.associatePackage(b -> b.packageID(id).domainName(DOMAIN_NAME));
            assertThat(associateResponse.sdkHttpResponse().isSuccessful()).isTrue();
            System.out.println("PackageStatus after association: " + OpenSearchHelper.getPackageStatus(id));
            System.out.println("DomainPackageStatus after association: " + OpenSearchHelper.getDomainPackageStatus(id));
            await().atMost(ofMinutes(10)).pollInterval(ofSeconds(5)).untilAsserted(() -> {
                var status = OpenSearchHelper.getDomainPackageStatus(id);
                System.out.println(LocalTime.now().withNano(0) + " Association status: " + status);
                assertThat(status).contains(ACTIVE);
            });

            System.out.println("PackageStatus before dissociation: " + OpenSearchHelper.getPackageStatus(id));
            System.out.println("DomainPackageStatus before dissociation: " + OpenSearchHelper.getDomainPackageStatus(id));
            var dissociateResponse = OpenSearchHelper.client.dissociatePackage(b -> b.packageID(id).domainName(DOMAIN_NAME));
            assertThat(dissociateResponse.sdkHttpResponse().isSuccessful()).isTrue();
            System.out.println("PackageStatus after dissociation: " + OpenSearchHelper.getPackageStatus(id));
            System.out.println("DomainPackageStatus after dissociation: " + OpenSearchHelper.getDomainPackageStatus(id));
            await().atMost(ofMinutes(10)).pollInterval(ofSeconds(5)).untilAsserted(() -> {
                var status = OpenSearchHelper.getDomainPackageStatus(id);
                System.out.println(LocalTime.now().withNano(0) + " Dissociation status: " + status);
                assertThat(status).isEmpty();
            });
        }
    }

}

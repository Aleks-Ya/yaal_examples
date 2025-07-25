package aws2.opensearch.packages;

import aws2.s3.OpenSearchHelper;
import aws2.s3.S3Helper;
import org.junit.jupiter.api.Test;
import software.amazon.awssdk.services.opensearch.model.PackageDetails;
import software.amazon.awssdk.services.opensearch.model.UpdatePackageRequest;

import java.time.LocalTime;

import static aws2.s3.OpenSearchHelper.DOMAIN_NAME;
import static aws2.s3.OpenSearchHelper.KEY;
import static aws2.s3.OpenSearchHelper.client;
import static java.time.Duration.ofMinutes;
import static java.time.Duration.ofSeconds;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static software.amazon.awssdk.core.sync.RequestBody.fromString;
import static software.amazon.awssdk.services.opensearch.model.DomainPackageStatus.ACTIVE;

class UpdatePackageIT {

    @Test
    void updatePackage() {
        try (var pac = OpenSearchHelper.createPackage()) {
            var packageDetails = pac.packageDetails;
            var id = packageDetails.packageID();

            assertThat(client.associatePackage(b1 -> b1.packageID(id).domainName(DOMAIN_NAME))
                    .sdkHttpResponse().isSuccessful()).isTrue();
            await().atMost(ofMinutes(10)).pollInterval(ofSeconds(5)).untilAsserted(() -> {
                var status = OpenSearchHelper.getDomainPackageStatus(id);
                System.out.println(LocalTime.now().withNano(0) + " Association status: " + status);
                assertThat(status).contains(ACTIVE);
            });

            System.out.println("AvailablePackageVersion before updating: " + OpenSearchHelper.getPackageDetails(id).map(PackageDetails::availablePackageVersion));
            System.out.println("PackageStatus before updating: " + OpenSearchHelper.getPackageStatus(id));
            System.out.println("DomainPackageStatus before updating: " + OpenSearchHelper.getDomainPackageStatus(id));
            var newContent = "tree,grass,plant\n";
            var bucket = pac.bucket.name;
            assertThat(S3Helper.s3.putObject(o -> o.bucket(bucket).key(KEY), fromString(newContent))
                    .sdkHttpResponse().isSuccessful()).isTrue();
            var updateRequest = UpdatePackageRequest.builder().packageID(id).packageSource(b -> b.s3BucketName(bucket).s3Key(KEY)).build();
            var updateResponse = client.updatePackage(updateRequest);
            assertThat(updateResponse.sdkHttpResponse().isSuccessful()).isTrue();
            System.out.println("AvailablePackageVersion after updating: " + OpenSearchHelper.getPackageDetails(id).map(PackageDetails::availablePackageVersion));
            System.out.println("PackageStatus after updating: " + OpenSearchHelper.getPackageStatus(id));
            System.out.println("DomainPackageStatus after updating: " + OpenSearchHelper.getDomainPackageStatus(id));

            assertThat(client.dissociatePackage(b -> b.packageID(id).domainName(DOMAIN_NAME))
                    .sdkHttpResponse().isSuccessful()).isTrue();
            await().atMost(ofMinutes(10)).pollInterval(ofSeconds(5)).untilAsserted(() -> {
                var status = OpenSearchHelper.getDomainPackageStatus(id);
                System.out.println(LocalTime.now().withNano(0) + " Dissociation status: " + status);
                assertThat(status).isEmpty();
            });
        }
    }

}

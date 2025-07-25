package aws2.opensearch.packages;

import aws2.s3.OpenSearchHelper;
import aws2.s3.S3Helper;
import org.junit.jupiter.api.Test;
import software.amazon.awssdk.services.opensearch.OpenSearchClient;
import software.amazon.awssdk.services.opensearch.model.CreatePackageRequest;
import software.amazon.awssdk.services.opensearch.model.DeletePackageRequest;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static software.amazon.awssdk.core.sync.RequestBody.fromString;
import static software.amazon.awssdk.services.opensearch.model.PackageType.TXT_DICTIONARY;

class CreatePackageIT {

    @Test
    void createDeletePackage() {
        try (var bucket = S3Helper.randomBucket()) {
            try (var client = OpenSearchClient.builder().build()) {
                var key = "packages/package1.txt";
                assertThat(S3Helper.s3.putObject(o -> o.bucket(bucket.name).key(key), fromString("car,vehicle,truck\n")).sdkHttpResponse().isSuccessful()).isTrue();
                var packageName = "package-tmp-" + UUID.randomUUID();
                System.out.println("Package: " + packageName);
                var request = CreatePackageRequest.builder()
                        .packageName(packageName)
                        .packageType(TXT_DICTIONARY)
                        .packageSource(b -> b.s3BucketName(bucket.name).s3Key(key))
                        .build();
                var response = client.createPackage(request);
                assertThat(response.sdkHttpResponse().isSuccessful()).isTrue();
                var packageDetails = response.packageDetails();
                var id = packageDetails.packageID();
                var name = packageDetails.packageName();
                var type = packageDetails.packageType();
                var status = packageDetails.packageStatus();
                var version = packageDetails.availablePackageVersion();
                System.out.println(id + " " + name + " " + type + " " + status + " " + version);

                var deleteRequest = DeletePackageRequest.builder().packageID(id).build();
                var deleteResponse = client.deletePackage(deleteRequest);
                assertThat(deleteResponse.sdkHttpResponse().isSuccessful()).isTrue();
            }
        }
    }

    @Test
    void createDeletePackageWithHelper() {
        try (var pac = OpenSearchHelper.createPackage()) {
            var packageDetails = pac.packageDetails;
            var id = packageDetails.packageID();
            var name = packageDetails.packageName();
            var type = packageDetails.packageType();
            var status = packageDetails.packageStatus();
            var version = packageDetails.availablePackageVersion();
            System.out.println(id + " " + name + " " + type + " " + status + " " + version);
        }
    }

}

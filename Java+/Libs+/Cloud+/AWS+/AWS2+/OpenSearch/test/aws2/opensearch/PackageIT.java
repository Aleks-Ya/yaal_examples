package aws2.opensearch;

import aws2.s3.S3Helper;
import org.junit.jupiter.api.Test;
import software.amazon.awssdk.services.opensearch.OpenSearchClient;
import software.amazon.awssdk.services.opensearch.model.CreatePackageRequest;
import software.amazon.awssdk.services.opensearch.model.DeletePackageRequest;
import software.amazon.awssdk.services.opensearch.model.DescribePackagesFilter;
import software.amazon.awssdk.services.opensearch.model.DescribePackagesRequest;
import software.amazon.awssdk.services.opensearch.model.ListPackagesForDomainRequest;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static software.amazon.awssdk.core.sync.RequestBody.fromString;
import static software.amazon.awssdk.services.opensearch.model.DescribePackagesFilterName.PACKAGE_NAME;
import static software.amazon.awssdk.services.opensearch.model.PackageType.TXT_DICTIONARY;

class PackageIT {
    @Test
    void describePackages() {
        try (var client = OpenSearchClient.builder().build()) {
            var response = client.describePackages(DescribePackagesRequest.builder().build());
            var packageList = response.packageDetailsList();
            packageList.forEach(packageDetails -> {
                var id = packageDetails.packageID();
                var name = packageDetails.packageName();
                var type = packageDetails.packageType();
                var status = packageDetails.packageStatus();
                var version = packageDetails.availablePackageVersion();
                System.out.println(id + " " + name + " " + type + " " + status + " " + version);
            });
        }
    }

    @Test
    void listPackagesForDomain() {
        try (var client = OpenSearchClient.builder().build()) {
            var response = client.listPackagesForDomain(ListPackagesForDomainRequest.builder().domainName("domain-1").build());
            var domainPackageList = response.domainPackageDetailsList();
            domainPackageList.forEach(packageDetails -> {
                var id = packageDetails.packageID();
                var name = packageDetails.packageName();
                var type = packageDetails.packageType();
                var status = packageDetails.domainPackageStatus();
                var version = packageDetails.packageVersion();
                System.out.println(id + " " + name + " " + type + " " + status + " " + version);
            });
        }
    }

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
    void getPackageState() {
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

                var describeFilter = DescribePackagesFilter.builder().name(PACKAGE_NAME).value(packageName).build();
                var describeResponse = client.describePackages(b -> b.filters(describeFilter));
                assertThat(describeResponse.hasPackageDetailsList()).isTrue();
                var status = describeResponse.packageDetailsList().getFirst().packageStatus();
                System.out.println("Status: " + status);

                assertThat(client.deletePackage(b -> b.packageID(id)).sdkHttpResponse().isSuccessful()).isTrue();
            }
        }
    }

    @Test
    void getStateOfAbsentPackage() {
        try (var client = OpenSearchClient.builder().build()) {
            var absentPackageName = "package-tmp-" + UUID.randomUUID();
            var describeFilter = DescribePackagesFilter.builder().name(PACKAGE_NAME).value(absentPackageName).build();
            var describeResponse = client.describePackages(b -> b.filters(describeFilter));
            var hasPackage = describeResponse.hasPackageDetailsList();
            assertThat(hasPackage).isFalse();
        }
    }
}

package aws2.opensearch.packages;

import aws2.s3.OpenSearchHelper;
import org.junit.jupiter.api.Test;
import software.amazon.awssdk.services.opensearch.OpenSearchClient;
import software.amazon.awssdk.services.opensearch.model.DescribePackagesFilter;
import software.amazon.awssdk.services.opensearch.model.DescribePackagesRequest;
import software.amazon.awssdk.services.opensearch.model.ListPackagesForDomainRequest;

import java.util.UUID;

import static aws2.s3.OpenSearchHelper.DOMAIN_NAME;
import static org.assertj.core.api.Assertions.assertThat;
import static software.amazon.awssdk.services.opensearch.model.DescribePackagesFilterName.PACKAGE_ID;
import static software.amazon.awssdk.services.opensearch.model.DescribePackagesFilterName.PACKAGE_NAME;

class ListPackagesIT {

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
            var response = client.listPackagesForDomain(ListPackagesForDomainRequest.builder().domainName(DOMAIN_NAME).build());
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
    void getPackageStateById() {
        try (var pac = OpenSearchHelper.createPackage()) {
            var packageId = pac.packageDetails.packageID();
            var describeFilter = DescribePackagesFilter.builder().name(PACKAGE_ID).value(packageId).build();
            var describeResponse = OpenSearchHelper.client.describePackages(b -> b.filters(describeFilter));
            assertThat(describeResponse.hasPackageDetailsList()).isTrue();
            var status = describeResponse.packageDetailsList().getFirst().packageStatus();
            System.out.println("Status: " + status);
        }
    }

    @Test
    void getPackageStateByName() {
        try (var pac = OpenSearchHelper.createPackage()) {
            var packageName = pac.packageDetails.packageName();
            var describeFilter = DescribePackagesFilter.builder().name(PACKAGE_NAME).value(packageName).build();
            var describeResponse = OpenSearchHelper.client.describePackages(b -> b.filters(describeFilter));
            assertThat(describeResponse.hasPackageDetailsList()).isTrue();
            var status = describeResponse.packageDetailsList().getFirst().packageStatus();
            System.out.println("Status: " + status);
        }
    }

    @Test
    void getPackageStateWithHelper() {
        try (var pac = OpenSearchHelper.createPackage()) {
            var packageId = pac.packageDetails.packageID();
            var status = OpenSearchHelper.getPackageStatus(packageId);
            System.out.println("Status: " + status);
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

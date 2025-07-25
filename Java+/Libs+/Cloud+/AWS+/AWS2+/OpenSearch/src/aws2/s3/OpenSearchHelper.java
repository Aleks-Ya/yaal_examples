package aws2.s3;

import software.amazon.awssdk.services.opensearch.OpenSearchClient;
import software.amazon.awssdk.services.opensearch.model.CreatePackageRequest;
import software.amazon.awssdk.services.opensearch.model.DescribePackagesFilter;
import software.amazon.awssdk.services.opensearch.model.DomainPackageDetails;
import software.amazon.awssdk.services.opensearch.model.DomainPackageStatus;
import software.amazon.awssdk.services.opensearch.model.ListPackagesForDomainRequest;
import software.amazon.awssdk.services.opensearch.model.PackageDetails;
import software.amazon.awssdk.services.opensearch.model.PackageStatus;

import java.util.Optional;
import java.util.UUID;

import static software.amazon.awssdk.core.sync.RequestBody.fromString;
import static software.amazon.awssdk.services.opensearch.model.DescribePackagesFilterName.PACKAGE_ID;
import static software.amazon.awssdk.services.opensearch.model.PackageType.TXT_DICTIONARY;

public class OpenSearchHelper {
    public static final String DOMAIN_NAME = "domain-1";
    public static final String KEY = "packages/package1.txt";
    public static final OpenSearchClient client = OpenSearchClient.builder().build();

    public static OpenSearchPackage createPackage(String content) {
        return new OpenSearchPackage(content);
    }

    public static OpenSearchPackage createPackage() {
        return createPackage("car,vehicle,truck\n");
    }

    public static Optional<PackageDetails> getPackageDetails(String packageId) {
        var filter = DescribePackagesFilter.builder().name(PACKAGE_ID).value(packageId).build();
        var response = client.describePackages(b -> b.filters(filter));
        if (response.hasPackageDetailsList()) {
            return Optional.of(response.packageDetailsList().getFirst());
        } else {
            return Optional.empty();
        }
    }

    public static Optional<PackageStatus> getPackageStatus(String packageId) {
        return getPackageDetails(packageId).map(PackageDetails::packageStatus);
    }

    public static Optional<DomainPackageStatus> getDomainPackageStatus(String packageId) {
        var response = client.listPackagesForDomain(ListPackagesForDomainRequest.builder().domainName(DOMAIN_NAME).build());
        assert response.sdkHttpResponse().isSuccessful();
        var domainPackageList = response.domainPackageDetailsList();
        return domainPackageList.stream()
                .filter(dp -> dp.packageID().equals(packageId))
                .findFirst()
                .map(DomainPackageDetails::domainPackageStatus);
    }

    private static PackageDetails createPackage(String bucket, String content) {
        var putObjectResponse = S3Helper.s3.putObject(o -> o.bucket(bucket).key(KEY), fromString(content));
        assert putObjectResponse.sdkHttpResponse().isSuccessful();
        var packageName = "package-tmp-" + UUID.randomUUID();
        System.out.println("Package: " + packageName);
        var request = CreatePackageRequest.builder()
                .packageName(packageName)
                .packageType(TXT_DICTIONARY)
                .packageSource(b -> b.s3BucketName(bucket).s3Key(KEY))
                .build();
        var response = client.createPackage(request);
        assert response.sdkHttpResponse().isSuccessful();
        var packageDetails = response.packageDetails();
        System.out.printf("Created package: id=%s, name='%s'\n", packageDetails.packageID(), packageDetails.packageName());
        return packageDetails;
    }

    private static void deletePackage(PackageDetails packageDetails) {
        System.out.println("Deleting package: " + packageDetails.packageName());
        assert client.deletePackage(b -> b.packageID(packageDetails.packageID())).sdkHttpResponse().isSuccessful();
    }

    public static class OpenSearchPackage implements AutoCloseable {
        public final S3Helper.RandomBucket bucket;
        public final PackageDetails packageDetails;

        private OpenSearchPackage(String content) {
            bucket = S3Helper.randomBucket();
            packageDetails = createPackage(bucket.name, content);
        }

        @Override
        public void close() {
            bucket.close();
            deletePackage(packageDetails);
        }
    }
}

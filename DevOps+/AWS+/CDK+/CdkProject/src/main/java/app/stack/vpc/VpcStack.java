package app.stack.vpc;

import software.amazon.awscdk.Stack;
import software.amazon.awscdk.Tags;
import software.amazon.awscdk.services.ec2.IpAddresses;
import software.amazon.awscdk.services.ec2.SubnetConfiguration;
import software.amazon.awscdk.services.ec2.Vpc;
import software.constructs.Construct;

import java.util.List;

import static software.amazon.awscdk.services.ec2.SubnetType.PRIVATE_ISOLATED;
import static software.amazon.awscdk.services.ec2.SubnetType.PUBLIC;

public class VpcStack extends Stack {
    public VpcStack(final Construct scope) {
        super(scope, VpcStack.class.getSimpleName(), null);
        var name = "vpc-cdk-1";
        var subnetName1 = "CdkPublicSubnet1";
        var subnetName2 = "CdkPrivateSubnet1";
        var vpc = Vpc.Builder.create(this, "VPC resource creation")
                .vpcName(name)
                .ipAddresses(IpAddresses.cidr("10.0.0.0/16"))
                .subnetConfiguration(List.of(
                        SubnetConfiguration.builder()
                                .name(subnetName1)
                                .subnetType(PUBLIC)
                                .build(),
                        SubnetConfiguration.builder()
                                .name(subnetName2)
                                .subnetType(PRIVATE_ISOLATED)
                                .build()
                ))
                .maxAzs(1)
                .build();
        for (var subnet : vpc.getPublicSubnets()) Tags.of(subnet).add("Name", subnetName1);
        for (var subnet : vpc.getIsolatedSubnets()) Tags.of(subnet).add("Name", subnetName2);
    }
}

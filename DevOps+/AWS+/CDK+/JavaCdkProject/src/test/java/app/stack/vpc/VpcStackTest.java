package app.stack.vpc;// package com.myorg;

import org.junit.jupiter.api.Test;
import software.amazon.awscdk.App;
import software.amazon.awscdk.assertions.Template;

import java.util.List;

import static java.util.Map.of;

class VpcStackTest {
    @Test
    void testStack() {
        var app = new App();
        var stack = new VpcStack(app);
        var template = Template.fromStack(stack);
        var vpcType = "AWS::EC2::VPC";
        template.resourceCountIs(vpcType, 1);
        template.hasResourceProperties(vpcType, of(
                "CidrBlock", "10.0.0.0/16",
                "Tags", List.of(
                        of("Key", "Name", "Value", "vpc-cdk-1")
                )
        ));
        var subnetType = "AWS::EC2::Subnet";
        template.resourceCountIs(subnetType, 2);
    }
}

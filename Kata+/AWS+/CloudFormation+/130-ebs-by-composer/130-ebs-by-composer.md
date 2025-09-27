# 130-ebs-by-composer

## Task
Use CloudFormation to craete an EBS Volume with the Infrastructure Composer.

## Setup
1. "Create stack" -> "With new resources (standard)"
2. Prepare template: `Build from Infrastructure Composer`
	1. `Create a template in Infrastructure Composer`
		1. Create a Volume
			1. Drag `Resources`-`AWS::EC2::Volume`
			2. Logical ID: `VPC`
			3. Resource configuration:
			```yaml
			VolumeType: gp3
			Size: 1
			AvailabilityZone: us-east-1a
			Encrypted: false
			Tags: 
			  - Key: Name
			    Value: kata-vol-ebs-by-composer
			```
		2. Create Stack
			1. Click `Create template`
			2. Transfer bucket name: `kata-bucket-ebs-by-composer`
3. Stack name: `kata-stack-ebs-by-composer`
4. Click Submit

## Cleanup
1. Delete Stack `kata-stack-ebs-by-composer`
2. Delete Transfer Bucket `kata-bucket-ebs-by-composer`

# 180-ec2-instance-httpd-by-composer

## Task
Use CloudFormation to create an EC2 Instance running an httpd server with the Infrastructure Composer.

## Setup
1. "Create stack" -> "With new resources (standard)"
2. Prepare template: `Build from Infrastructure Composer`
	1. `Create a template in Infrastructure Composer`
		1. Create an EC2 Instance
			1. Drag `Resources`-`AWS::EC2::Instance`
			2. Resource configuration:
			```yaml
			ImageId: ami-08982f1c5bf93d976
			InstanceType: t2.micro
			UserData: !Base64 |
			  #!/bin/bash
			  dnf install -y httpd
			  systemctl enable httpd
			  systemctl start httpd
			  echo "My httpd page" > /var/www/html/index.html
			Tags:
			  - Key: Name
			    Value: kata-i-ec2-instance-httpd-by-composer
			```
		2. Create Stack
			1. Click `Create template`
			2. Transfer bucket name: `kata-bucket-ec2-instance-httpd-by-composer`
3. Stack name: `kata-stack-ec2-instance-httpd-by-composer`
4. Click Submit
5. Test web-server
    1. Copy "Public IPv4 address" from `kata-i-ec2-instance-httpd-by-composer`
    2. Test: `curl 100.26.47.158`

## Cleanup
1. Delete Stack `kata-stack-ec2-instance-httpd-by-composer`
2. Delete Transfer Bucket `kata-bucket-ec2-instance-httpd-by-composer`

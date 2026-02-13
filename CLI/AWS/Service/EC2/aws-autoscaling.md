# AWS Auto-Scaling Group CLI

List ASGs: `aws autoscaling describe-auto-scaling-groups`
Show an ASG details: `aws autoscaling describe-auto-scaling-groups --auto-scaling-group-names <ASG_NAME>`
List activities in an ASG: `aws autoscaling describe-scaling-activities --auto-scaling-group-name <ASG_NAME>`
Create an ASG: 
```shell
aws autoscaling create-auto-scaling-group \
	--auto-scaling-group-name my-asg \
	--launch-template LaunchTemplateName=my-lt,Version=1 \
	--min-size 1 --max-size 3 --desired-capacity 1 \
	--vpc-zone-identifier "subnet-aaa,subnet-bbb"
```
Delete an ASG: `aws autoscaling delete-auto-scaling-group --auto-scaling-group-name $ASG --force-delete`

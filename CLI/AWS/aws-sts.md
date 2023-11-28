# AWS STS CLI

Show current user details: `aws sts get-caller-identity`
Show other user details: `aws --profile Programmer sts get-caller-identity`

Assume role: `aws sts assume-role --role-arn arn:aws:iam::523633434047:role/service-role/hello-role-sw7s8c3f --role-session-name session1`

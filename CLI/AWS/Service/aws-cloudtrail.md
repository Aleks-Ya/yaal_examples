# AWS CloudTrail CLI

List all Trails: `aws cloudtrail list-trails`
Create a Trail for S3 Bucket: `aws cloudtrail create-trail --name my-trail --s3-bucket-name my-bucket`
Delete a Trail: `aws cloudtrail delete-trail --name s3-trail`
Start logging a Trail: `aws cloudtrail start-logging --name s3-trail`
Show last 3 events: `aws cloudtrail lookup-events --max-results 3`

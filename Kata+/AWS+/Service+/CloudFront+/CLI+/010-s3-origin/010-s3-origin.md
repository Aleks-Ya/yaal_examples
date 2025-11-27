# 010-s3-origin

## Task
Setup CloudFront Destination with S3 Bucket Origin (with AWS CLI).

## Steps
1. Change the current directory
2. Create an S3 Bucket:
    1. Create bucket: `aws s3 mb s3://tmp-kata-s3-origin`
    2. Upload a file: `echo MyData | aws s3 cp - s3://tmp-kata-s3-origin/data.txt`
2. Create a Destination
    1. Create an OAC:
        1. Create OAC: `export OAC=$(aws cloudfront create-origin-access-control --origin-access-control-config file://oac-config.json)`
        2. Get OAC ID: `export OAC_ID=$(echo $OAC | jq -r .OriginAccessControl.Id)`
        3. Get OAC ETag: `export OAC_ETAG=$(echo $OAC | jq -r .ETag)`
        4. Substitute OAC ID to Distribution config: `envsubst < distribution-config-template.json > distribution-config.json`
    2. Create a distribution: 
        1. Create distribution: `export DIST=$(aws cloudfront create-distribution --distribution-config file://distribution-config.json)`
        2. Get Distribution ID: `export DIST_ID=$(echo $DIST | jq -r .Distribution.Id)`
        3. Get Distribution Domain Name: `export DOMAIN_NAME=$(echo $DIST | jq -r .Distribution.DomainName)`
        4. Get Distribution ETag: `export ETAG=$(echo $DIST | jq -r .ETag)`
    3. Create bucket policy: 
        1. Substitute Distribution ID to Bucket policy: `envsubst < bucket-policy-template.json > bucket-policy.json`
        2. Put bucket policy: `aws s3api put-bucket-policy --bucket tmp-kata-s3-origin --policy file://bucket-policy.json` 
4. Test
    1. Wait for `Deployed` status: `aws cloudfront get-distribution --id $DIST_ID --query Distribution.Status`
    2. Get file: `curl https://$DOMAIN_NAME/data.txt`

## Cleanup
1. Delete Distribution: 
    1. Disable distribution:
        1. Get distribution config: `aws cloudfront get-distribution-config --id $DIST_ID | jq .DistributionConfig > distribution-config-current.json`
        2. Update config: `jq '.Enabled = false' distribution-config-current.json > distribution-config-disabled.json`
        2. Update distribution: `aws cloudfront update-distribution --id $DIST_ID --if-match $ETAG --distribution-config file://distribution-config-disabled.json`
        4. Wait for `Deployed` status: `aws cloudfront get-distribution --id $DIST_ID --query Distribution.Status`
    2. Delete distribution: 
        1. Get new ETag: `export ETAG2=$(aws cloudfront get-distribution-config --id $DIST_ID | jq -r .ETag)`
        2. Delete distribution: `aws cloudfront delete-distribution --id $DIST_ID --if-match $ETAG2`
2. Delete Origin Access: `aws cloudfront delete-origin-access-control --id $OAC_ID --if-match $OAC_ETAG`
3. Delete S3 Bucket: `aws s3 rb --force s3://tmp-kata-s3-origin`

## Errors
### Preconditions
Command: `aws cloudfront delete-distribution --id $DIST_ID --if-match $ETAG2`
Error message: `An error occurred (PreconditionFailed) when calling the DeleteDistribution operation: The request failed because it didn't meet the preconditions in one or more request-header fields.`
Cause: outdated ETag
Solution: get the latests Distritution ETag

## History

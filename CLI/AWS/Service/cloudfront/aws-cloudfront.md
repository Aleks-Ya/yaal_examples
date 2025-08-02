# AWS CloudFront CLI

## Distribution
List distributions: `aws cloudfront list-distributions`
Show distiribution details: `aws cloudfront get-distribution --id E1XAY13EKMUGB`
Show distribution config: `aws cloudfront get-distribution-config --id E1XAY13EKMUGB`
Show distribution status: `aws cloudfront get-distribution --id E1XAY13EKMUGB --query Distribution.Status`
Update distribution: `aws cloudfront update-distribution --id E1XAY13EKMUGB --if-match E2OPKSPM4RFAQ2 --distribution-config file://distribution-config.json`
Create a distribution: `aws cloudfront create-distribution --distribution-config file://distribution-config.json`
Delete a distribution: `aws cloudfront delete-distribution --id E1XAY13EKMUGB`

## OAC
List OACs: `aws cloudfront list-origin-access-controls`
Show OAC details: `aws cloudfront get-origin-access-control --id E21WY2JKC0IK6`
Get OAC ID by name: `aws cloudfront list-origin-access-controls --query "OriginAccessControlList.Items[?Name=='oac-1'].Id" --output text`
Create an OAC: `aws cloudfront create-origin-access-control --origin-access-control-config file://oac-config.json`
Delete an OCA: `aws cloudfront delete-origin-access-control --id E1NA4AI0NSYFML --if-match ETVPDKIKX0DER`

## OAI (legacy)
OAI format:
- In Distribution config: `"OriginAccessIdentity": "origin-access-identity/cloudfront/E2VRKKQVCF3DWS"`
- In Bucket policy: `arn:aws:iam::cloudfront:user/CloudFront Origin Access Identity E2VRKKQVCF3DWS`
List OAIs: `aws cloudfront list-cloud-front-origin-access-identities`
Get an OAI: `aws cloudfront get-cloud-front-origin-access-identity --id E2VRKKQVCF3DWS`

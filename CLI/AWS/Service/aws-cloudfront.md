# AWS CloudFront CLI

## Distribution
List distributions: `aws cloudfront list-distributions`
Show distiribution details: `aws cloudfront get-distribution --id E1XAY13EKMUGB`
Show distribution config: `aws cloudfront get-distribution-config --id E1XAY13EKMUGB`
Update distribution: `aws cloudfront update-distribution --id E1XAY13EKMUGB --if-match E2OPKSPM4RFAQ2 --distribution-config file://dist-config.json`

## OAC
List OACs: `aws cloudfront list-origin-access-controls`

## OAI
OAI format in Distribution config: `"OriginAccessIdentity": "origin-access-identity/cloudfront/E2VRKKQVCF3DWS"`
List OAIs: `aws cloudfront list-cloud-front-origin-access-identities`
Get an OAI: `aws cloudfront get-cloud-front-origin-access-identity --id E2VRKKQVCF3DWS`

# 030-static-web-site

## Task
Publish a static web-site from an S3 bucket and make it available from the Internet.

## Steps
1. Change current dir
2. Set env vars
	```shell
	set -x
	export BUCKET=kata-bucket-static-web-site
	```
3. Create S3 bucket: `aws s3 mb s3://$BUCKET`
4. Upload `index.html` to the bucket: `aws s3 cp index.html s3://$BUCKET`
5. Start web-site: `aws s3 website s3://$BUCKET --index-document index.html`
6. Disable Public Access Block: `aws s3api delete-public-access-block --bucket $BUCKET`
7. Allow read access: `aws s3api put-bucket-policy --bucket $BUCKET --policy file://bucket-policy.json`
8. Test: 
	1. Get current region: `export REGION=$(aws configure get region)`
	2. Test dash-region URL:
		1. Compose URL: `export DASH_REGION_URL=http://$BUCKET.s3-website-$REGION.amazonaws.com`
		2. Test web-site: `curl -i $DASH_REGION_URL`
	3. Test dot region URL: 
		1. Compose URL: `export DOT_REGION_URL=http://$BUCKET.s3-website.$REGION.amazonaws.com`
		2. Test web-site: `curl -i $DOT_REGION_URL`

## Cleanup
1. Delete S3 bucket: `aws s3 rb --force s3://$BUCKET`
2. Delete env variables: `set +x; unset BUCKET REGION DASH_REGION_URL DOT_REGION_URL`

## History
- 2025-11-16 success

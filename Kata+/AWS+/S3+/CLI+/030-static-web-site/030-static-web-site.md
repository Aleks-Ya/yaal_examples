# 030-static-web-site

## Task
Publish a static web-site from an S3 bucket and make it available from the Internet.

## Steps
1. Change the current directory
2. Create S3 bucket: `aws s3 mb s3://tmp-kata-static-web-site`
3. Upload `index.html` to the bucket: `aws s3 cp index.html s3://tmp-kata-static-web-site`
4. Start web-site: `aws s3 website s3://tmp-kata-static-web-site --index-document index.html`
5. Disable Public Access Block: `aws s3api delete-public-access-block --bucket tmp-kata-static-web-site`
6. Allow read access: `aws s3api put-bucket-policy --bucket tmp-kata-static-web-site --policy file://bucket-policy.json`
7. Test: 
	1. Get current region: `aws configure get region`
	2. Test dash region URL: `curl -i http://tmp-kata-static-web-site.s3-website-us-east-1.amazonaws.com`
	3. Test dot region URL: `curl -i http://tmp-kata-static-web-site.s3-website.us-east-1.amazonaws.com`

## Cleanup
1. Delete S3 bucket: `aws s3 rb --force s3://tmp-kata-static-web-site`

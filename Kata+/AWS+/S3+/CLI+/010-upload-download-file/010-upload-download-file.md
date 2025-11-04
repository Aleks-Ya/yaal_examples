# 010-upload-download-file

## Task
Using AWS CLI: upload file `picture.png` to new S3 bucket and download it to file `picture2.png`.

## Dependencies
Configure AWS CLI without permissions: `Kata+/AWS+/CLI+/001-configure-aws-cli-without-permissions/001-configure-aws-cli-without-permissions.md`

## Steps
1. AWS Console
	1. Add `AmazonS3FullAccess` policy to user `cli`
2. Terminal
	1. Check access from CLI: `awsd s3 ls`
	2. Create a bucket: `awsd s3 mb s3://kata-001-upload-download-file`
	3. Copy the picture to the Docker container shared directory: `cp picture.png /tmp/aws-cli-kata-share`
	4. Upload the picture: `awsd s3 cp /root/picture.png s3://kata-001-upload-download-file/picture.png`
	5. Download the picture: `awsd s3 cp s3://kata-001-upload-download-file/picture.png /root/picture2.png`
	6. Check the downloaded picture: `subl /tmp/aws-cli-kata-share/picture2.png`

## Clean up
1. Delete the bucket: `awsd s3 rb --force s3://kata-001-upload-download-file`
2. From dependency `Kata+/AWS+/CLI+/001-configure-aws-cli-without-permissions/001-configure-aws-cli-without-permissions.md`
2. 

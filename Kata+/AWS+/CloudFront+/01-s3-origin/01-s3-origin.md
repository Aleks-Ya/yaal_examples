# 01-s3-origin

## Task

Setup CloudFront Destination with S3 Bucket Origin.

## Setup

1. Create an S3 Bucket
    1. AWS Region: `us-east-1`
    2. Bucket type: `General purpose`
    3. Bucket name: `bucket-129123129992`
    4. Download a picture: `wget https://wallpaperaccess.com/full/670929.jpg -O /tmp/picture.jpg`
    5. Upload the picture in the Bucket: `aws s3 cp /tmp/picture.jpg s3://bucket-129123129992/picture.jpg`
2. Create a Destination
    1. Origin domain: `bucket-129123129992`
    2. Name: `bucket-129123129992.s3.us-east-1.amazonaws.com`
    3. Origin access: `Origin access control settings`
    4. Origin access control
        1. Create control settings
            1. Name: `bucket-129123129992.s3.us-east-1.amazonaws.com`
            2. Signing behavior: `Sign requests`
            3. Origin type: `S3`
    5. Web Application Firewall (WAF): `Do not enable security protections`
3. Configure Bucket Policy
    1. Press "Copy policy" button and paste the policy into the Bucket Policy
4. Test
    1. Construct picture URL using Distribution domain name: `https://d13qft8u2q8s1v.cloudfront.net/picture.jpg`
    2. Download the picture: `wget https://d13qft8u2q8s1v.cloudfront.net/picture.jpg -O /tmp/picture_out.jpg`

## Cleanup

1. Delete Distribution
2. Delete Origin Access
3. Delete S3 Bucket

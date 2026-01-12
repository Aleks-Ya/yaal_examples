# 010-s3-origin

## Task
Setup CloudFront Destination with S3 Bucket Origin.

## Steps
1. Create an S3 Bucket:
    1. Create bucket:
        1. AWS Region: `us-east-1`
        2. Bucket type: `General purpose`
        3. Bucket name: `kata-bucket-s3-origin`
    2. Upload file:
        1. Download a picture https://wallpaperaccess.com/full/670929.jpg
        2. Upload the picture in the Bucket as `picture.jpg`
2. Create a Destination:
    1. Plan: `Free`
    2. Name: `kata-distribution-s3-origin`
    2. Origin:
        1. Origin type: `Amazon S3`
        2. S3 origin: `kata-bucket-s3-origin.s3.us-east-1.amazonaws.com`
    3. Origin access: `Origin access control settings`
    4. Origin access control
        1. Create control settings ???????
            1. Name: `kata-bucket-s3-origin.s3.us-east-1.amazonaws.com`
            2. Signing behavior: `Sign requests`
            3. Origin type: `S3`
    5. Web Application Firewall (WAF): `Do not enable security protections`
3. Configure Bucket Policy
    1. Press "Copy policy" button and paste the policy into the Bucket Policy
4. Test
    1. Construct picture URL using Distribution domain name: `https://ds1qbskwai9tz.cloudfront.net/picture.jpg`
    2. Open picture in browser: https://ds1qbskwai9tz.cloudfront.net/picture.jpg
    3. Download the picture: `wget https://ds1qbskwai9tz.cloudfront.net/picture.jpg -O /tmp/picture_out.jpg`

## Cleanup
1. Delete Distribution
2. Delete Origin Access
3. Delete S3 Bucket

## History

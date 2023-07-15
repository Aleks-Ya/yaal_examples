# NOT WORK

# List files in a Google Cloud Storage bucket
# Boto v2 uses S3Connection for working with GCS
# This script automatically takes "aws_access_key_id" and "aws_secret_access_key" from "~/.boto" config.
from boto.s3.connection import S3Connection

conn = S3Connection(host='storage.googleapis.com')

bucket = conn.get_bucket('gs://yaal-standard-1')
for key in bucket.list():
    print(key.name)

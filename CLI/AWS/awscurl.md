# awscurl CLI

GitHub: https://github.com/okigan/awscurl

Install: 
- Via Brew: `brew install awscurl`
- Via PIP: `pip install -U awscurl`

Help: `awscurl -h`

## S3
List all buckets: `awscurl --service s3 https://s3.amazonaws.com`

## OpenSearch
Send a request to OpenSearch cluster: `awscurl --service es https://search-domain-1-umrjsmqxw5xpwuqeuarcb2yno4.aos.us-east-1.on.aws`
Create an index: `awscurl --service es -XPUT https://search-domain-1-umrjsmqxw5xpwuqeuarcb2yno4.aos.us-east-1.on.aws/index1`

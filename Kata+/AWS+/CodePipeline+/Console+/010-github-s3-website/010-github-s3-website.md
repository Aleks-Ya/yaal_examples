# 010-github-s3-website

## Task
Create a Pipeline that deploys an S3 Static Website from a GitHub repository.

## Steps
1. Create a GitHub repository
	1. Create a Repository
		1. Name: `kata-repo-github-s3-website`
		2. Choose visibility: `Private`
	2. Upload file to the Repository: `index.html` and `error.html`
2. Create a Connection
	1. Provider: `GitHub`
	2. Connection name: `kata-conn-github-s3-website`
	3. App Installation: 
		1. Click `Install a new app`
		2. Select Repository `kata-repo-github-s3-website`
3. Create an S3 Bucket
	1. Bucket name: `kata-bucket-github-s3-website`
	2. Block all public access: disabled
4. Create a Pipeline
	1. Pipeline settings
		1. Pipeline name: `kata-pipeline-github-s3-website`
		2. Execution mode: `Superseded`
		3. Service role: `New service role`
		4. Role name: `kata-role-github-s3-website`
		5. Allow AWS CodePipeline to create a service role so it can be used with this new pipeline: enabled
	2. Source
		1. Source provider: `GitHub (via GitHub App)`
		2. Connection: `kata-conn-github-s3-website`
		3. Repository name: `Aleks-Ya/kata-repo-github-s3-website`
		4. Default branch: `main`
		5. Enable automatic retry on stage failure: disabled
	3. Build - click "Skip build stage"
	4. Test - click "Skip test stage"
	5. Deploy
		1. Deploy provider: `Amazon S3`
		2. Region: `United States (N. Verginia)`
		3. Input artifacts: `SourceArtifact`
		4. Bucket: `kata-bucket-github-s3-website`
		5. S3 object key: empty
		6. Extract file before deploy: enabled
		7. Configure automatic rollback on stage failure: disabled
5. Enable Static Website hosting
	1. Set bucket policy
	```json
	{
    	"Version": "2012-10-17",
    	"Statement": [
        	{
            	"Effect": "Allow",
            	"Principal": "*",
            	"Action": "s3:GetObject",
            	"Resource": "arn:aws:s3:::kata-bucket-github-s3-website/*"
        	}
    	]
	}
	```
	2. Enable Static Website hosting
		1. Index document: `index.html`
		2. Error document: `error.html`
		3. Test using "Bucket website endpoint"
6. Deploy a new version
	1. Increase version in https://github.com/Aleks-Ya/kata-repo-github-s3-website/blob/main/index.html
	2. Wait a moment until the Pipeline detects the change and executes
	3. Test using "Bucket website endpoint"

## Cleanup
1. Delete Pipeline: `kata-pipeline-github-s3-website`
2. Delete Connection: `kata-conn-github-s3-website`
2. Delete GitHub repository: `kata-repo-github-s3-website`
2. Delete IAM Role: `kata-role-github-s3-website`
3. Delete S3 Bucket: `kata-bucket-github-s3-website`

# AWS configure CLI

Show current configuration data: `aws configure list`
List all profiles: `aws configure list-profiles`
Configure default profile: `aws configure`
Configure profile `Developer`: `aws configure --profile Developer`
Show default region: `aws configure get region`
Set default region: `aws configure set region eu-north-1`
Set default region (via env vars): `export AWS_DEFAULT_REGION=us-west-2`
Set default output format: `aws configure set default.output json`
Show default output format: `aws configure get default.output`
Get JSON with credentials: `aws configure export-credentials`
Delete profile: open `~/.aws/credentials` in text editor and remove profile
Get current key id: `aws configure get aws_access_key_id`

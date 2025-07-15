# AWS configure CLI

Show current configuration data: `aws configure list`
Get JSON with credentials: `aws configure export-credentials`
Delete profile: open `~/.aws/credentials` in text editor and remove profile
Get current key id: `aws configure get aws_access_key_id`

## Profile
List all profiles: `aws configure list-profiles`
Configure default profile: `aws configure`
Configure profile `Developer`: `aws configure --profile Developer`
Set current profile using env vars: `export AWS_PROFILE=Developer`

## Region
Show default region: `aws configure get region`
Set default region: `aws configure set region eu-north-1`
Set default region (via env vars): `export AWS_DEFAULT_REGION=us-west-2`

## Output format
Show default output format: `aws configure get default.output`
Set default output format: `aws configure set default.output json`

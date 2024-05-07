# 03-grant-access-to-folder-for-user

## Task
Grant read-write access to a folder in a bucket for a user.

## Setup
1. Create user
	1. Create a user: `aws iam create-user --user-name user1`
	2. Create an access key for the user: `aws iam create-access-key --user-name user1`
	3. Configure profile: `aws --profile user1 configure`
2. Create bucket
	1. Create a bucket: `aws s3 mb s3://03-grant-access-to-folder-for-user`
	2. Upload file (accessible): `echo abc | aws s3 cp - s3://03-grant-access-to-folder-for-user/dir1/data.txt`
	3. Upload file (inaccessible): `echo abc | aws s3 cp - s3://03-grant-access-to-folder-for-user/dir2/secret.txt`
3. Grant access
	1. Create a policy: `aws iam create-policy --policy-name Dir1Policy --policy-document file://policy.json`
	2. Attach the policy to the user: `aws iam attach-user-policy --policy-arn arn:aws:iam::523633434047:policy/Dir1Policy --user-name user1`
3. Test access
	1. Can do:
		1. List files: `aws --profile user1 s3 ls s3://03-grant-access-to-folder-for-user/dir1/`
		2. Get file content: `aws --profile user1 s3 cp s3://03-grant-access-to-folder-for-user/dir1/data.txt -`
		2. Update file: `echo xyz | aws --profile user1 s3 cp - s3://03-grant-access-to-folder-for-user/dir1/data.txt`
		3. Delete file: `aws s3 rm s3://03-grant-access-to-folder-for-user/dir1/data.txt`
	2. Cannot do:
		1. Get file from another folder: `aws --profile user1 s3 cp s3://03-grant-access-to-folder-for-user/dir2/secret.txt -`

## Cleanup
1. Delete bucket: `aws s3 rb s3://03-grant-access-to-folder-for-user --force`
2. Delete the policy:
	1. Detach the policy: `aws iam detach-user-policy --user-name user1 --policy-arn arn:aws:iam::523633434047:policy/Dir1Policy`
	2. Delete policy: `aws iam delete-policy --policy-arn arn:aws:iam::523633434047:policy/Dir1Policy`
3. Delete the user:
	1. Delete access key: `aws iam delete-access-key --user-name user1 --access-key-id AKIAIOSFODNN7EXAMPLE`
	2. Delete user: `aws iam delete-user --user-name user1`
4. Unlogin AWS CLI: remove section `[user1]` from `~/.aws/credentials`

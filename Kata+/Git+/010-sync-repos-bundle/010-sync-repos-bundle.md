# 010-sync-repos-bundle

## Task
Transfer commits from a local repo to a remote repo without network connection via a bundle.

## Steps
1. Set environment variables
	```shell
	set -x
	export LOCAL_NAME=kata-repo-sync-repos-bundle-local
	export REMOTE_NAME=kata-repo-sync-repos-bundle-remote
	export LOCAL_PATH=/tmp/$LOCAL_NAME
	export REMOTE_PATH=/tmp/$REMOTE_NAME
	```
2. Create a remote repo
	1. Open a new terminal
	2. Set environment variables
	3. Create a dir: `mkdir -p $REMOTE_PATH`
	4. Change current dir: `cd $REMOTE_PATH`
	5. Init repo: `git init`
3. Create a local repo
	1. Open a new terminal
	2. Set environment variables
	3. Create a dir: `mkdir -p $LOCAL_PATH`
	4. Change current dir: `cd $LOCAL_PATH`
	5. Init repo: `git init`
	6. Add a remote repo: `git remote add origin file://$REMOTE_PATH`
4. Make changes in the local repo
	1. Create a file: `echo aaa > data.txt`
	2. Stage the file: `git add data.txt`
	3. Commit the file: `git commit -m CommitA`
5. Push branch from local repo to remote repo:
	1. Create a branch: `git checkout -b feature1`
	2. Push the branch: `git push --set-upstream origin feature1`
6. Send local changes to the remote repo by a bundle:
	1. In the local repo:
		1. Create another commit: 
			1. Update file: `echo bbb > data.txt`
			2. Stage the file: `git add data.txt`
			3. Commit the file: `git commit -m CommitB`
		2. Create a bundle: `git bundle create /tmp/changes.bundle origin/feature1..feature1`
		3. Inspect the bundle: `git bundle list-heads /tmp/changes.bundle`
	2. In the remote repo:
		1. Show original branch: `git log feature1`
		2. Verify the bundle: `git bundle verify /tmp/changes.bundle`
		3. Apply the bundle: `git fetch /tmp/changes.bundle feature1:feature1`
		4. Show updated branch: `git log feature1`

## Cleanup
1. Delete the remote repo: `rm -rf $REMOTE_PATH`
2. Delete the local repo: `rm -rf $LOCAL_PATH`

## History
- 2025-12-15 success

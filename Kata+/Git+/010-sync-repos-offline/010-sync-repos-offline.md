# 010-sync-repos-offline

## Task
Transfer commits from a local repo to a remote repo without network connection.

## Steps
1. Set environment variables
	```shell
	set -x
	export LOCAL_NAME=kata-repo-sync-repos-offline-local
	export REMOTE_NAME=kata-repo-sync-repos-offline-remote
	export LOCAL_PATH=/tmp/$LOCAL_NAME
	export REMOTE_PATH=/tmp/$REMOTE_NAME
	```
2. Create a remote repo
	1. Open a new terminal
	2. Set environment variables
	3. Create a dir: `mkdir -p $REMOTE_PATH`
	4. Change current dir: `cd $REMOTE_PATH`
	5. Init repo: `git init`
	6. Init commit: ``
3. Create a local repo
	1. Open a new terminal
	2. Set environment variables
	3. Create a dir: `mkdir -p $LOCAL_PATH`
	4. Change current dir: `cd $LOCAL_PATH`
	5. Init repo: `git init`
	6. Add a remote repo: `git remote add origin file://$REMOTE_PATH`
3. Make changes in the local repo
	1. Create a file: `echo aaa > data.txt`
	2. Stage the file: `git add data.txt`
	3. Commit the file: `git commit -m CommitA`
4. Push branch from local repo to remote repo:
	1. Create a branch: `git checkout -b feature1`
	2. Push the branch: `git push --set-upstream origin feature1`
5. Send local changes to the remote repo
.....

## Cleanup
1. Delete the remote repo: `rm -rf $REMOTE_PATH`
2. Delete the local repo: `rm -rf $LOCAL_PATH`

## History

# GitStatus

1. Create the remote Git repo:
	```shell
	export REMOTE_REPO=/tmp/git-status-repo
	rm -rf $REMOTE_REPO
	mkdir $REMOTE_REPO
	cd $REMOTE_REPO
	git init
	echo aaa > a.txt
	git add a.txt
	git commit -m "Init commit"
	```
2. Change currect directory
3. Clean: `mvn clean`
3. Checkout: `mvn scm:checkout`
3. Create an uncommited file: `touch /tmp/GitStatus/b.txt`
4. Add the file: `mvn scm:add -Dincludes=target/checkout/a.txt`
3. Show status: `mvn scm:status`

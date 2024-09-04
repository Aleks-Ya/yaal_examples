# git CLI

## Help
Show help about a command in terminal: `git help submodule`
### Help in web-broser
Install: `sudo apt-get install git-doc`
Show help about a command in browser: `git help -w submodule` (not all commands are available)

## Troubleshooting
Verbose: `git push -v`
Curl verbose: `GIT_CURL_VERBOSE=1 git push`
Another trace options: 
- GIT_TRACE for general traces,
- GIT_TRACE_PACK_ACCESS for tracing of packfile access,
- GIT_TRACE_PACKET for packet-level tracing for network operations,
- GIT_TRACE_PERFORMANCE for logging the performance data,
- GIT_TRACE_SETUP for information about discovering the repository and environment it’s interacting with,
- GIT_MERGE_VERBOSITY for debugging recursive merge strategy (values: 0-5),
- GIT_CURL_VERBOSE for logging all curl messages (equivalent to curl -v),
- GIT_TRACE_SHALLOW for debugging fetching/cloning of shallow repositories.
Possible values can include:   true, 1 or 2 to write to stderr,  an absolute path starting with / to trace output to the specified file.

## Checkout
Remove local changes in a file: `git checkout db.changelog-insert.yaml`

## Log
Log one line: `git log --pretty=oneline master`
Log shows committer: `git log --pretty=full`

## Patch
Create patch from the last commit: `git format-patch -1`
Create a commit from a patch: `git am 0001-update.patch`

## Clean
Drop untracked files (dry-run): `git clean -n -df`
Drop untracked files (execute): `git clean -df`

## Bundle
Create a bundle with all refs (brances, tags, etc.): `git bundle create b1.bundle --all`
Create a bundle with all objects in a branch: `git bundle create b1.pack master`
Create a bundle with last 2 objects in a branch: `git bundle create b1.pack  master~2..master`
List heads in a bundle: `git bundle list-heads b1.pack`
Verify a bundle: `git bundle verify b1.pack`
Apply a bundle: `git bundle unbundle b1.pack`

## Update Ref
Set commit to which a ref points: `git update-ref refs/remotes/origin/main d31d4efb8456327c4797bc17af527e967e051199`
Delete a ref: `git update-ref -d refs/remotes/origin/main2`

## Show Refs
List all refs: `git show-ref`

## Config
Show all configured properties: `git config --list`
Show value of a config property: `git config --get-all user.email`
Set property value: `git config push.default upstream`
Set username: `git config --global user.name "Aleksey Yablokov"`
Set email: `git config --global user.email alex_ya@mailbox.org`
Unset a property: `git config --unset http.sslcainfo`

## SubTree
Create a subproject: 

## Commit
Change the author of the last commit: `git commit --amend --author="Mark <mark@address.com>" --no-edit`

## Credentials
Use storage credential helper: `git config --global credential.helper store`
Remove expired credentials:
1. Run `git credential reject`
2. Enter:
```
protocol=https
host=bitbucket.prd.company.net
```

## Stash
Clear stash: `git stash clear`
List previous 5 entries: `git stash list -5`
List modified files in specific entry: `git stash show stash@{0}`
Apply latest entry: `git stash apply`
Apply specific entry: `git stash apply stash@{0}`
Save local changes to an entry: `git stash push` = `git stash` (`git save` is deprecated)
Delete an entry: `git stash drop stash@{0}`

## Archive
Pack branch into a ZIP (without `.git`): `git archive --output a.zip master`

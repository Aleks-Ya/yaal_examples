# Encrypting files with Sub Keys scenario
Create a Master key. Create Sub Keys for the Master Key. Encrypt files with Sub Key.

1. Init home dir
	1. Choose a home dir: `export HDIR=/tmp/sub-key-test-case`
	2. Clear the home dir: `rm -rf $HDIR`
	3. Create a home dir: `mkdir -p $HDIR && chmod 700 $HDIR`
	4. Init a GPG home dir: `gpg --homedir $HDIR --list-keys`
2. Generate a master key (USER ID will be `"John Smith (Master key of John Smith) <smith@mail.com>"`)
	1. Invoke: `gpg --homedir $HDIR --full-generate-key`
	2. `kind of key` = `(1) RSA and RSA (default)`
	3. `keysize` = `4096`
	4. `valid` = `0 = key does not expire`
	5. `Real name` = `John Smith`
	6. `Email address` = `smith@mail.com`
	7. `Comment` = `Master key of John Smith`
	8. Pass phrase = `john99`
3. Find Master Key ID: 
	1. List all keys: `gpg --homedir $HDIR --list-keys`
	2. Set env var: `export MKID=8151108964EDF56DC57389A80BFE4FF8441CEE06`
4. Add a subordinate key
	1. Run: `gpg --homedir $HDIR --edit-key $MKID`
		2. `addkey`
			3. `kind of key` = `(6) RSA (encrypt only)`
			4. `keysize` = `4096`
			5. `valid` = `0 = key does not expire`
		6. `save`
	2. Find the sub-key: 
		1. List sub-keys: `gpg --homedir $HDIR --list-secret-keys --with-subkey-fingerprints`
		2. Set env var: `export SKID=3FF83A5DD2AF6EEC5CDD851313D01C7387BAE6E7`
	3. Export public sub-key: `gpg --homedir $HDIR --output subordinate-public-key.asc --armor --export $SKID`
	4. Set permissions on the sub-key file: `chmod u=rw,go= subordinate-public-key.asc`
5. Encrypt a file with the sub key
	1. Create a file: `echo "abc" > origin.txt`
	2. Encrypt file: `gpg --homedir $HDIR --encrypt --recipient $SKID --output encrypted.txt.gpg origin.txt`
	3. Decrypt file: `gpg --homedir $HDIR -d -o decrypted.txt encrypted.txt.gpg`
	4. Compare files: `cmp origin.txt decrypted.txt`

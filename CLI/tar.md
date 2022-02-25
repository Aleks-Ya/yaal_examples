# tar CLI

Unpack

Unpack tar.gz file:
```
mkdir  /tmp/dest_dir/
tar -zxvf /tmp/hadoop.tar.gz -C /tmp/dest_dir/
```
Unpack tag.xz file:
```
mkdir out
tar -xf crc-linux-amd64.tar.xz -C out
```
List archive content:
```
tar -tf configs.tar.gz
```

Pack

Create tag.gz:


Errors

```
gzip: stdin: not in gzip format
tar: Child returned status 1
tar: Error is not recoverable: exiting now
```
Reason: trying to unpack XZ as GZ
# tar CLI

## Info
Help: `tar --help`

## View
List archive content: `tar -tf configs.tar.gz`

## Pack
Pack several files: `tar -cf out.tar a.txt b.txt`
Pack a directory (with top directory): `tar -cf out.tar my_dir` (`my_dir` folder is present in the archive)
Pack a directory (without top directory): `tar -cf out.tar -C my_dir .` (`my_dir` folder is NOT present in the archive)

## Unpack
Unpack `tar.gz` file:
```
mkdir  /tmp/dest_dir/
tar -zxvf /tmp/hadoop.tar.gz -C /tmp/dest_dir/
```
Unpack `tag.xz` file:
```
mkdir out
tar -xf crc-linux-amd64.tar.xz -C out
```

## Errors
```
gzip: stdin: not in gzip format
tar: Child returned status 1
tar: Error is not recoverable: exiting now
```
Reason: trying to unpack XZ as GZ
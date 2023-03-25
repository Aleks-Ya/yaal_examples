# tar CLI

## Parameters
- `-t`, `--list`							list the contents of an archive
- `-x`, `--extract`, `--get`				extract files from an archive
- `-v`, `--verbose`							verbosely list files processed
- `-f`, `--file`							use archive file
- `-z`, `--gzip`, `--gunzip`, `--ungzip`	filter the archive through gzip
- `-c`, `--create`							create a new archive
- `-d`, `--diff`, `--compare`				find differences between archive and file system

## Info
Help: `tar --help`

## View
List archive content: `tar -tf configs.tar.gz`

## Verify integrity
By verbose list: `tar -tvf archive.tar > /dev/null`
By unpacking: `tar -tf my_tar.tar > /dev/null`
By diff (specific directory): `tar -df archive.tar /original/dir`
By diff (current directory): `tar -df archive.tar`

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

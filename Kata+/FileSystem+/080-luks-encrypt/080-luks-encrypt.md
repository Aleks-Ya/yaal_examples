# 080-luks-encrypt

## Task
Encrypt a Block Device using LUKS.

## Setup
1. Set environment variables
```shell
set -x
export DEVICE_FILE=/tmp/luks-encrypt.img
export MOUNT_POINT=/mnt/luks-encrypt
export DEVICE_MAPPER_NAME=luks-encrypt-disk
export DEVICE_MAPPER_PATH=/dev/mapper/$DEVICE_MAPPER_NAME
```
2. Create a Loop Device
	1. Allocate a backing file: `fallocate -l 100M $DEVICE_FILE`
	2. Associate the file with a Loop Device: `export DEVICE_ID=$(sudo losetup --find --show $DEVICE_FILE)`
3. Encrypt the Device: 
	1. Encrypt: `sudo cryptsetup luksFormat $DEVICE_ID`
	2. Passphrase: `pass1`
4. Open the encrypted Device: `sudo cryptsetup luksOpen $DEVICE_ID $DEVICE_MAPPER_NAME`
5. Create a FS: `sudo mkfs.ext4 $DEVICE_MAPPER_PATH`
6. Mount the FS:
	1. Create a mount point: `sudo mkdir $MOUNT_POINT`
	2. Mount: `sudo mount $DEVICE_MAPPER_PATH $MOUNT_POINT`
7. Use the partition
	1. Create a file: `sudo su -c "echo abc > $MOUNT_POINT/data.txt"`
	2. Read the file: `cat $MOUNT_POINT/data.txt`
8. Test decryption
	1. Close the device:
		1. Unmount: `sudo umount $MOUNT_POINT`
		2. Close: `sudo cryptsetup close $DEVICE_MAPPER_NAME`
	2. Re-open
		1. Open the device: `sudo cryptsetup luksOpen $DEVICE_ID $DEVICE_MAPPER_NAME`
		2. Mount: `sudo mount $DEVICE_MAPPER_PATH $MOUNT_POINT`
		3. Read the file: `cat $MOUNT_POINT/data.txt`
9. Review in GParted: 
	1. Device: `sudo gparted $DEVICE_ID`
	2. Partition: `sudo gparted $DEVICE_MAPPER_PATH`

## Cleanup
1. Unmount the partition: 
	1. Unmount: `sudo umount $MOUNT_POINT`
	2. Delete the mount point: `sudo rm -rf $MOUNT_POINT`
2. Close the device: `sudo cryptsetup close $DEVICE_MAPPER_NAME`
3. Delete the Loop Device:
	1. Detach the loop device: `sudo losetup -d $DEVICE_ID`
	2. Delete the file: `rm $DEVICE_FILE`
4. Unset env vars: `unset DEVICE_FILE MOUNT_POINT DEVICE_MAPPER_NAME DEVICE_MAPPER_PATH DEVICE_ID`

## History
- 2025-10-26 success

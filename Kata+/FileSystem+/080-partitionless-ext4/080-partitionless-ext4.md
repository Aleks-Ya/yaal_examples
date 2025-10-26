# 080-partitionless-ext4

## Task
Create an `ext4` File System on a disk without a Partition Table.

## Setup
1. Set environment variables
```shell
set -x
export DEVICE_FILE=/tmp/partitionless-ext4.img
export MOUNT_POINT=/mnt/partitionless-ext4
```
2. Create a Loop Device
	1. Allocate a backing file: `fallocate -l 100M $DEVICE_FILE`
	2. Associate the file with a Loop Device: `export DEVICE_ID=$(sudo losetup --find --show $DEVICE_FILE)`
	3. Show new device: `lsblk $DEVICE_ID`
3. Create a FS: `sudo mkfs.ext4 $DEVICE_ID`
2. Mount the FS:
	1. Create a mount point: `sudo mkdir $MOUNT_POINT`
	2. Mount: `sudo mount $DEVICE_ID $MOUNT_POINT`
3. Use the partition
	1. Create a file: `sudo su -c "echo abc > $MOUNT_POINT/data.txt"`
	2. Read the file: `cat $MOUNT_POINT/data.txt`
4. Review in GParted: `sudo gparted $DEVICE_ID`

## Cleanup
1. Unmount the partition: 
	1. Unmount: `sudo umount $MOUNT_POINT`
	2. Delete the mount point: `sudo rm -rf $MOUNT_POINT`
2. Delete the Loop Device:
	1. Detach the loop device: `sudo losetup -d $DEVICE_ID`
	2. Delete the file: `rm $DEVICE_FILE`
3. Unset env vars: `unset DEVICE_FILE MOUNT_POINT DEVICE_ID`

## History
- 2025-10-26 success

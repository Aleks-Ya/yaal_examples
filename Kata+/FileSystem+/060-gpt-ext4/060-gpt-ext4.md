# 060-gpt-ext4

## Task
Create a GPT Partition Table with an `ext4` File System.

## Setup
1. Set environment variables
```shell
set -x
export DEVICE_FILE=/tmp/gpt-ext4.img
export MOUNT_POINT=/mnt/gpt-ext4
```
2. Create a Loop Device
	1. Allocate a backing file: `fallocate -l 100M $DEVICE_FILE`
	2. Associate the file with a Loop Device: `export DEVICE_ID=$(sudo losetup --find --show $DEVICE_FILE)`
	3. Show new device: `lsblk $DEVICE_ID`
3. Create a Partition Table
	1. Create Partition Table: `sudo parted $DEVICE_ID mklabel gpt`
	2. Create a Partition: `sudo parted --align optimal $DEVICE_ID mkpart primary ext4 1MiB 100%`
	3. Prepare Partition ID: `export PARTITION_ID=${DEVICE_ID}p1`
4. Create a FS: `sudo mkfs.ext4 $PARTITION_ID`
5. Mount the FS:
	1. Create a mount point: `sudo mkdir $MOUNT_POINT`
	2. Mount: `sudo mount $PARTITION_ID $MOUNT_POINT`
6. Use the partition
	1. Create a file: `sudo su -c "echo abc > $MOUNT_POINT/data.txt"`
	2. Read the file: `cat $MOUNT_POINT/data.txt`
7. Review in GParted: `sudo gparted $DEVICE_ID`

## Cleanup
1. Unmount the partition: 
	1. Unmount: `sudo umount $MOUNT_POINT`
	2. Delete the mount point: `sudo rm -rf $MOUNT_POINT`
2. Delete the Loop Device:
	1. Detach the loop device: `sudo losetup -d $DEVICE_ID`
	2. Delete the file: `rm $DEVICE_FILE`
3. Unset env vars: `unset DEVICE_FILE MOUNT_POINT DEVICE_ID PARTITION_ID`

## History
- 2025-10-26 success

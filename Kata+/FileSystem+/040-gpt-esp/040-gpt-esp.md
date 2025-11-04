# 040-gpt-esp

## Task
Create an EFI System Partition (ESP).

## Steps
1. Set environment variables
```shell
set -x
export DEVICE_FILE=/tmp/gpt-esp.img
export MOUNT_POINT=/mnt/gpt-esp
```
2. Create a Loop Device
	1. Allocate a backing file: `fallocate -l 100M $DEVICE_FILE`
	2. Associate the file with a Loop Device: `export DEVICE_ID=$(sudo losetup --find --show $DEVICE_FILE)`
3. Create a Partition Table
	1. Create Partition Table: `sudo parted $DEVICE_ID mklabel gpt`
	2. Create a ESP: `sudo parted $DEVICE_ID mkpart ESP fat32 1MiB 50MiB`
	3. Set ESP flag: `sudo parted $DEVICE_ID set 1 esp on`
4. Create a FS: 
	1. Refresh partition table: `sudo partprobe $DEVICE_ID`
	2. Prepare Partition ID: `export PARTITION_ID=${DEVICE_ID}p1`
	3. Format ESP to FAT32: `sudo mkfs.fat -F32 $PARTITION_ID`
	4. Show partitions: `sudo parted $DEVICE_ID print`
5. Mount ESP:
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
- 2025-10-27 success

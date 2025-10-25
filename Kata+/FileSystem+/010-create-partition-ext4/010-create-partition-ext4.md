# 010-create-partition-ext4

## Task
Create a File System with an ext4 Partition.

## Setup
1. Create a virtual file system
	1. Allocate a file: `fallocate -l 100M /tmp/create-partition-ext4.img`
	2. Associate the file with a Loop Device: `sudo losetup --find --show /tmp/create-partition-ext4.img`
	3. Remember the device: e.g. `/dev/loop19`
	4. Verify new device: `lsblk`
2. Create a partition
	1. Create a partition table
		1. Open Gparted: `sudo gparted /dev/loop19`
		2. Device - Create Partition Table...
			1. Partition table type: `gpt`
	2. Create a partition
		1. Create as: `Primary Partition`
		2. Partition name: `NAME1`
		3. File system: `ext4`
		4. Label: `LABEL1`
	3. Apply all operations
	4. Verify partition: `lsblk /dev/loop19`
3. Use the partition
	1. Mount the partition
		1. Create a mount point: `sudo mkdir /mnt/create-partition-ext4`
		2. Mount partition: `sudo mount /dev/loop19p1 /mnt/create-partition-ext4`
	2. Use partition
		1. Create a file: `sudo su -c "echo abc > /mnt/create-partition-ext4/data.txt"`
		2. Read the file: `cat /mnt/create-partition-ext4/data.txt`

## Cleanup
1. Unmount the partition: `sudo umount /mnt/create-partition-ext4`
2. Delete the mount point: `sudo rm -rf /mnt/create-partition-ext4`
3. Detach the loop device: `sudo losetup -d /dev/loop19`
4. Delete the file: `rm /tmp/create-partition-ext4.img`

## History
- 2025-10-25 success

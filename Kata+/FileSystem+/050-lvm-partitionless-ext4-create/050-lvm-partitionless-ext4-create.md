# 050-lvm-partitionless-ext4-create

## Task
Create a Partition using LVM.

## Setup
1. Set environment variables
```shell
set -x
export DEVICE_FILE=/tmp/lvm-create.img
export VG_NAME=kata_vg_lvm_create
export LV_NAME=kata_lv_lvm_create
export LV_PATH=/dev/$VG_NAME/$LV_NAME
export MOUNT_POINT=/mnt/lvm-create
```
2. Create a Loop Device
	1. Allocate a backing file: `fallocate -l 100M $DEVICE_FILE`
	2. Associate the file with a Loop Device: `export DEVICE_ID=$(sudo losetup --find --show $DEVICE_FILE)`
	3. Show new device: `lsblk $DEVICE_ID`
3. Create a PV (Physical Volume): 
	1. Create: `sudo pvcreate $DEVICE_ID`
	2. Show: `sudo pvdisplay $DEVICE_ID`
4. Create a VG (Volume Group): 
	1. Create: `sudo vgcreate $VG_NAME $DEVICE_ID`
	2. Show: `sudo vgdisplay -v $VG_NAME`
5. Create a LV (Logical Volume): 
	1. Create: `sudo lvcreate -L 50M -n $LV_NAME $VG_NAME`
	2. Show: `sudo lvdisplay $LV_PATH`
6. Use the LV:
	1. Format LV: `sudo mkfs.ext4 $LV_PATH`
	2. Mount LV:
		1. Create a mount point: `sudo mkdir $MOUNT_POINT`
		2. Mount: `sudo mount $LV_PATH $MOUNT_POINT`
	3. Use the partition
		1. Create a file: `sudo su -c "echo abc > $MOUNT_POINT/data.txt"`
		2. Read the file: `cat $MOUNT_POINT/data.txt`
	4. Review in GParted: `sudo gparted $DEVICE_ID`

## Cleanup
1. Unmount the partition: 
	1. Unmount: `sudo umount $MOUNT_POINT`
	2. Delete the mount point: `sudo rm -rf $MOUNT_POINT`
2. Delete LV: `sudo lvremove $LV_PATH`
3. Delete VG: `sudo vgremove $VG_NAME`
4. Delete PV: `sudo pvremove $DEVICE_ID`
5. Delete the Loop Device:
	1. Detach the loop device: `sudo losetup -d $DEVICE_ID`
	2. Delete the file: `rm $DEVICE_FILE`
6. Unset env vars: `unset DEVICE_FILE VG_NAME LV_NAME LV_PATH MOUNT_POINT DEVICE_ID`

## History
- 2025-10-26 success
- 2025-10-25 success

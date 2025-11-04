# 070-lvm-partitionless-ext4-reduce

## Task
Reduce the size of a Logical Volume (LVM).

## Steps
1. Set environment variables
```shell
set -x
export DEVICE_FILE=/tmp/lvm-reduce.img
export VG_NAME=kata_vg_lvm_reduce
export LV_NAME=kata_lv_lvm_reduce
export LV_PATH=/dev/$VG_NAME/$LV_NAME
export MOUNT_POINT=/mnt/lvm-reduce
```
2. Create an LV
	1. Create a Loop Device
		1. Allocate a backing file: `fallocate -l 100M $DEVICE_FILE`
		2. Associate the file with a Loop Device: `export DEVICE_ID=$(sudo losetup --find --show $DEVICE_FILE)`
	2. Create a PV: `sudo pvcreate $DEVICE_ID`
	3. Create a VG: `sudo vgcreate $VG_NAME $DEVICE_ID`
	4. Create a LV: `sudo lvcreate -L 70M -n $LV_NAME $VG_NAME`
	5. Use the LV:
		1. Format LV: `sudo mkfs.ext4 $LV_PATH`
		2. Mount LV:
			1. Create a mount point: `sudo mkdir $MOUNT_POINT`
			2. Mount: `sudo mount $LV_PATH $MOUNT_POINT`
		3. Use the partition
			1. Create a file: `sudo su -c "echo abc > $MOUNT_POINT/data.txt"`
			2. Read the file: `cat $MOUNT_POINT/data.txt`
3. Reduce the LV (unmount is required)
	1. Reduce the file system:
		1. Show size before: `df -h $MOUNT_POINT`
		2. Unmount: `sudo umount $MOUNT_POINT`
		2. Check the FS: `sudo e2fsck -f $LV_PATH`
		3. Resize: `sudo resize2fs $LV_PATH 50M`
		4. Mount: `sudo mount $LV_PATH $MOUNT_POINT`
		5. Show size after: `df -h $MOUNT_POINT`
	2. Reduce LV:
		1. Show sizes before: `sudo vgdisplay -v $VG_NAME`
		2. Optionally: unmount
		3. Reduce LV: `sudo lvreduce -L -20M $LV_PATH`
		4. Show sizes after: `sudo vgdisplay -v $VG_NAME`
	3. Read the file: `cat $MOUNT_POINT/data.txt`

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
- 2025-10-16 success

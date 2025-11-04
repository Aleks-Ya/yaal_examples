# 100-grub-install

## Task
Install GRUB on a Disk.

## Steps
1. Set environment variables
```shell
set -x
export DEVICE_FILE=/tmp/grub-install.img
export MOUNT_POINT=/mnt/esp-grub-install
export ESP_MOUNT_POINT=$MOUNT_POINT/boot/efi
```
2. Create a Loop Device
	1. Allocate a backing file: `fallocate -l 1G $DEVICE_FILE`
	2. Associate the file with a Loop Device: `export DEVICE_ID=$(sudo losetup --find --show $DEVICE_FILE)`
3. Create a Partition Table
	1. Create Partition Table: `sudo parted $DEVICE_ID mklabel gpt`
	2. Create an ESP:
		1. Create a ESP: `sudo parted $DEVICE_ID mkpart ESP fat32 1MiB 100MiB`
		2. Set ESP flag: `sudo parted $DEVICE_ID set 1 esp on`
		3. Set env var: `export ESP_ID=${DEVICE_ID}p1`
	2. Create a System Partition: 
		1. Create: `sudo parted $DEVICE_ID mkpart primary ext4 100MiB 100%`
		2. Set env var: `export SYSTEM_PARTITION_ID=${DEVICE_ID}p2`
4. Create FS: 
	1. Format ESP: `sudo mkfs.fat -F32 $ESP_ID`
	2. Format System Partition: `sudo mkfs.ext4 $SYSTEM_PARTITION_ID`
5. Mount FS:
	1. Mount System Partition:
		1. Create a mount point: `sudo mkdir $MOUNT_POINT`
		2. Mount: `sudo mount $SYSTEM_PARTITION_ID $MOUNT_POINT`
	2. Mount ESP:
		1. Create a mount point: `sudo mkdir -p $ESP_MOUNT_POINT`
		2. Mount: `sudo mount $ESP_ID $ESP_MOUNT_POINT`
6. Install GRUB:
```shell
sudo grub-install \
  --target=x86_64-efi \
  --efi-directory=$ESP_MOUNT_POINT \
  --boot-directory=$MOUNT_POINT/boot \
  --removable \
  --no-nvram \
  --root-directory=$MOUNT_POINT
```
7. Verify: `sudo file -s $DEVICE_ID`
8. Review in GParted: `sudo gparted $DEVICE_ID`

## Cleanup
1. Unmount partitions:
	1. Unmount ESP: `sudo umount $ESP_MOUNT_POINT`
	2. Unmount System Partition: `sudo umount $MOUNT_POINT`
	3. Delete the mount point: `sudo rm -rf $MOUNT_POINT`
2. Delete the Loop Device:
	1. Detach the loop device: `sudo losetup -d $DEVICE_ID`
	2. Delete the file: `rm $DEVICE_FILE`
3. Unset env vars: `unset DEVICE_FILE MOUNT_POINT ESP_MOUNT_POINT DEVICE_ID ESP_ID SYSTEM_PARTITION_ID`

## History
- 2025-10-29 success

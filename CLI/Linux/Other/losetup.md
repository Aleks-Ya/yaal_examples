# losetup CLI

Help: `losetup --help`

List all Loop Devices: `losetup -l`
Show specific Loop Device: `losetup -l /dev/loop2`
Show an unused Loop Device: `sudo losetup --find`
Detach a device: `sudo losetup -d /dev/loop20`

## Associate a Loop Device with a Backing File
1. Allocate a backing file: `fallocate -l 10M /tmp/backing.img`
2. Associate the file with a Loop Device: 
	1. Option 1: find unused device: `sudo losetup --find --show /tmp/backing.img`
	2. Option 2: use given device: `sudo losetup /dev/loop200 /tmp/backing.img`
3. Verify devices: `lsblk`

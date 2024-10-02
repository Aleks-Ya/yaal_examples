# mount CLI

Connect to EPAM_Images shared folder
```
sudo mount -t cifs --verbose //epam.com/sam/SoftArchive/EPAM_Images /home/aleks/tmp/smb/ -o username=Aleksei_Iablokov,uid=1000
```

RAM drive (in-memory disk)

Mount a RAM drive:
```
sudo mkdir /tmp/ramdisk
sudo mount -t tmpfs -o size=5G ramdisk /tmp/ramdisk
#Check: df -h
#Copy files to /tmp/ramdisk dir
```
Unmount a RAM drive: `sudo umount /tmp/ramdisk/`

## Error
### No such file or directory
Message: `Error "mount error(2): No such file or directory"`
Solution: Install `sudo apt-get install keyutils` ([source](https://unix.stackexchange.com/a/507464/220284))

### Unable to access "ADATA"
Command: open `ADATA` in the file manager or  `sudo mount -t ntfs-3g /dev/sda1 /media/aleks/ADATA`
Message:
```
Unable to access "ADATA"
Error mounting /dev/sda1 at /media/aleks/ADATA: wrong fs type, bad option, bad superblock on /dev/sda1, missing codepage or helper program, or other error
```
Solution: create directory `sudo mkdir -p /media/aleks/ADATA`

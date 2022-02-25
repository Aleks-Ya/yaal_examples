# mount CLI

Error "mount error(2): No such file or directory"

Solve: Install "sudo apt-get install keyutils" ([source](https://unix.stackexchange.com/a/507464/220284))

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
Unmount a RAM drive:
```
sudo umount /tmp/ramdisk/
```

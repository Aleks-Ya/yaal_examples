# ntfsfix

Error message:
```
$MFTMirr does not match $MFT (record 3).
Failed to mount '/dev/sda1': Input/output error
NTFS is inconsistent. Run chkdsk /f on Windows then reboot it TWICE!
The usage of the /f parameter is very IMPORTANT! No modification was
made to NTFS by this software.

Failed to open '/dev/sda1'.

$MFTMirr does not match $MFT (record 3).
Failed to mount '/dev/sda1': Input/output error
NTFS is inconsistent. Run chkdsk /f on Windows then reboot it TWICE!
The usage of the /f parameter is very IMPORTANT! No modification was
made to NTFS by this software.

Unable to read the contents of this file system!
Because of this some operations may be unavailable.
The cause might be a missing software package.
The following list of software packages is required for ntfs file system support:  ntfs-3g / ntfsprogs.
```

Fix on Ubuntu: `sudo ntfsfix /dev/sda1`

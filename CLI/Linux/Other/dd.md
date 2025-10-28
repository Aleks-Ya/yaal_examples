# dd CLI

Help: `dd --help`

Install Windows 11 to a flash drive:
```shell
sudo dd bs=4M status=progress oflag=sync \
	if=/media/aleks/ADATA/distr/os/Win11_25H2_EnglishInternational_x64.iso \
	of=/dev/sdb 
```

# clamscan CLI

## Docs
Man: https://linux.die.net/man/1/clamscan

## Install
CLI: `sudo apt install -y clamav`
GUI: `sudo apt install -y clamtk`

## Command
Help: `clamscan -h`
Version: `clamscan --version`
Refresh signatures: see `freshclam.md`
Scan current folder: `clamscan --suppress-ok-results --recursive .`
Scan single file: `clamscan eicar.com.txt`
See signature update date: `clamscan --version`

# rclone CLI

## Info
Site: https://rclone.org

## Install
### Via APT
Execute: `sudo apt install -y rclone`

### Via Snap (obsolete)
1. Execute `sudo snap install rclone`
2. Add `export RCLONE_CONFIG=~/snap/rclone/common/.rclone.conf` to `~/.bashrc`

Help: `rclone --help`
Help about a command: `rclone copy --help`
Version: `rclone version`

List configured remotes: `rclone listremotes`
Add new storage: `rclone config`

List all files in a remote: `rclone ls yandexdisk:/`
List all directories in a remote: `rclone lsd yandexdisk:/`
Calcuate folder size and file count: `rclone size yandexdisk:/Wakeboard`
Create folder: `rclone mkdir yandexdisk:/tmp1`
Delete an empty dir: `rclone rmdir yandexdisk:/tmp1`
Delete directory recursively: `rclone purge yandexdisk:/tmp1`

Copy folder from one remote to another: `rclone copy --progress yandexdisk:/Фотокамера pcloud:/tmp1` (files from `Фотокамера` appears inside `tmp1`)

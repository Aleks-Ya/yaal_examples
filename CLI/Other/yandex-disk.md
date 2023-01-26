# Yandex Disk Linux CLI

Install: 
1. download DEB from https://repo.yandex.ru/yandex-disk/
2. Login: `yandex-disk token aleks-iablokov`
3. Append `~/.bashrc`:
```
#alias yd-daemon-start='yandex-disk start --dir=~/yandex-disk/ --exclude-dirs=backup,learn,photo,zotero'
#alias yd-daemon-start='yandex-disk start --dir=~/yandex-disk/'
#alias ali='yandex-disk stop --dir=~/yandex-disk/'
alias yd-sync='~/pr/home/yaal_examples/Bash+/apps/yd-sync.sh'
alias yd-status='yandex-disk status --dir=~/yandex-disk/'
```

Help: `yandex-disk -h`
Version: `yandex-disk -v`
Login: `yandex-disk token aleks-iablokov`
Sync everything and exit: `yandex-disk sync --dir=~/yandex-disk/ --exclude-dirs=learn,backup`
Show status: `yandex-disk status --dir=~/yandex-disk/`
Run sync daemon (permanently): `yandex-disk start --dir=~/yandex-disk/ --exclude-dirs=learn,backup`
Stop sync daemon (permanently): `yandex-disk stop --dir=~/yandex-disk/ --exclude-dirs=learn,backup`

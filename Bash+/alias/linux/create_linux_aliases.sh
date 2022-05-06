# run this file from .~/bashrc

alias yd-sync='~/pr/home/yaal_examples/Bash+/apps/yd-sync.sh'
alias yd-status='yandex-disk status --dir=~/yandex-disk/'

alias mtree='mvn dependency:tree -DoutputFile=target/tree.txt'
alias fetch_all='python3 /home/aleks/pr/home/yaal_examples/Python+/Python3/src/apps/fetch_git_repos/fetch_git_repos.py /home/aleks/pr'
alias upgrade='sudo apt update && sudo apt upgrade -y && sudo apt autoremove -y'

alias backup_cryptomator='duplicity incremental --encrypt-key alex.yablokov@protonmail.com /home/aleks/Vault file:///home/aleks/yandex-disk/backup/CryptomatorVaultBackup'

alias vtb-report='
(VERSION=2.13; eval /home/aleks/.sdkman/candidates/java/16-open/bin/java -jar \
    -Dreport.source.dir=/home/aleks/Vault/Finances/VTB_Broker/VTB_Broker_Report/Origin \
    -Dexporter.dealxls.output.dir=/home/aleks/Vault/Finances/VTB_Broker/VTB_Broker_Report/Generated \
    /home/aleks/.m2/repository/ru/yaal/vtb/vtb-broker-report/${VERSION}/vtb-broker-report-${VERSION}.jar)'

alias gpc='globalprotect connect -u aleksei_iablokov@epam.com --portal vpn.epam.com'
alias gpd='globalprotect disconnect'

alias madata='mkdir -p /mnt/adata && sudo mount $(lsblk -o label,path --noheadings | grep ADATA | sed 's/ADATA\s*//') /mnt/adata/'
alias uadata='sudo umount /mnt/adata/'

alias load_cam_video='python3 /home/aleks/pr/home/yaal_examples/Python+/Python3/src/apps/mts2mp4/mts2mp4.py /media/aleks/JVCCAM_SD/PRIVATE/AVCHD/BDMV/STREAM /media/aleks/ADATA/tmp/Wakeboarding'

export PATH=$PATH:/media/aleks/ADATA/installed/crc_open_shift

#THIS MUST BE AT THE END OF THE FILE FOR SDKMAN TO WORK!!!
export SDKMAN_DIR="/home/aleks/.sdkman"
[[ -s "/home/aleks/.sdkman/bin/sdkman-init.sh" ]] && source "/home/aleks/.sdkman/bin/sdkman-init.sh"
export SDKMAN_OFFLINE_MODE=false
alias gps='globalprotect show --status'

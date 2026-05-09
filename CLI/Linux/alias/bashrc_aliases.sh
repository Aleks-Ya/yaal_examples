# Load from ~/.bashrc:
# . /home/aleks/pr/home/yaal_examples/CLI/Linux/alias/bashrc_aliases.sh

alias fetch_all='python3 /home/aleks/pr/home/yaal_examples/Python+/Python3/src/apps/fetch_git_repos/fetch_git_repos.py /home/aleks/pr'

alias load_cam_video_no_audio='python3 /home/aleks/pr/home/yaal_examples/Python+/Python3/src/apps/mts2mp4/mts2mp4.py /media/aleks/JVCCAM_SD /home/aleks/tmp/Wakeboarding'
alias load_cam_video_with_audio='python3 /home/aleks/pr/home/yaal_examples/Python+/Python3/src/apps/mts2mp4/mts2mp4.py /media/aleks/JVCCAM_SD /home/aleks/tmp/Wakeboarding --preserve-audio'
alias tagspaces_all='tagspaces_delete_zero_size_thumbnails; tagspaces_find_dirs_with_absent_thumbnails; tagspaces_rename_files'
alias tagspaces_delete_zero_size_thumbnails='python3 ~/pr/home/yaal_examples/Python+/Python3/src/apps/tag_spaces/delete_zero_size_thumbnails.py /media/aleks/ADATA/pcloud/Wakeboard/TagSpaces'
alias tagspaces_find_dirs_with_absent_thumbnails='python3 ~/pr/home/yaal_examples/Python+/Python3/src/apps/tag_spaces/find_dirs_with_absent_thumbnails.py /media/aleks/ADATA/pcloud/Wakeboard/TagSpaces'
alias tagspaces_rename_files='python3 ~/pr/home/yaal_examples/Python+/Python3/src/apps/tag_spaces/rename_files.py /media/aleks/ADATA/pcloud/Wakeboard/TagSpaces'

alias upgrade='~/pr/home/yaal_examples/Bash+/apps/upgrade_linux.sh'

alias backup_zotero='cd ~ && duplicity incremental --progress --encrypt-key CE0CE6B2 /home/aleks/Zotero s3://yaal-backup/duplicity-backup-zotero'
alias backup_docs_vault='cd ~ && duplicity incremental --progress --encrypt-key CE0CE6B2 /home/aleks/DocsVault s3://yaal-backup/duplicity-backup-docs-vault'
alias backup_joplin='cd ~ && duplicity incremental --progress --encrypt-key CE0CE6B2 /home/aleks/.config/joplin-desktop s3://yaal-backup/duplicity-backup-joplin-desktop'
alias backup_thunderbird='cd ~ && duplicity incremental --progress --encrypt-key CE0CE6B2 ~/.thunderbird s3://yaal-backup/duplicity-backup-thunderbird'

#Qt5
alias anki-user2-qt5-24.04.1="~/installed/Anki/anki-24.04.1-linux-qt5/anki -b ~/anki-home-dirs/user2/anki-24.04.1-linux-qt5"
alias anki-user2-qt5-24.06.3="~/installed/Anki/anki-24.06.3-linux-qt5/anki -b ~/anki-home-dirs/user2/anki-24.06.3-linux-qt5"
alias anki-user2-qt5-24.06="~/installed/Anki/anki-24.06-linux-qt5/anki -b ~/anki-home-dirs/user2/anki-24.06-linux-qt5"
alias anki-user2-qt5-24.10="~/installed/Anki/anki-24.10-linux-qt5/anki -b ~/anki-home-dirs/user2/anki-24.10-linux-qt5"

#Qt6
alias anki-user2-qt6-24.04.1="~/installed/Anki/anki-24.04.1-linux-qt6/anki -b ~/anki-home-dirs/user2/anki-24.04.1-linux-qt6"
alias anki-user2-qt6-24.06.3="~/installed/Anki/anki-24.06.3-linux-qt6/anki -b ~/anki-home-dirs/user2/anki-24.06.3-linux-qt6"
alias anki-user2-qt6-24.10="~/installed/Anki/anki-24.10-linux-qt6/anki -b ~/anki-home-dirs/user2/anki-24.10-linux-qt6"
alias anki-user2-qt6-25.02="~/installed/Anki/anki-25.02-linux-qt6/anki -b ~/anki-home-dirs/user2/anki-25.02-linux-qt6"
alias anki-user2-qt6-25.05="~/installed/Anki/anki-25.05-linux-qt6/anki -b ~/anki-home-dirs/user2/anki-25.05-linux-qt6"

alias avro-tools='java -jar ~/installed/avro-tools/avro-tools.jar'
alias draw="python ~/pr/home/yaal_examples/Python+/Python3/src/apps/libre_office_draw_search/draw_find.py"
alias examples='python ~/pr/home/yaal_examples/Python+/Python3/src/apps/yaal_examples_search/examples_search.py'

alias mtree='mvn dependency:tree -DoutputFile=target/tree.txt'

# ln CLI (symbolic link)

Create symbolic link (folder to folder):
```
#Directory "/link/folder" will be created ("/link/folder" == "/source/folder" by content)
ln -s /source/folder /link/
```
Delete symbolic link to a folder (no `/` in the end): `unlink /link/folder`
Delete symbolic link to a file: `unlink /path/to/file.txt`

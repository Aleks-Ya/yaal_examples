# cadaver CLI

Install: `sudo apt install -y cadaver`

Help: `cadaver -h`
Connect to server: `cadaver https://webdav.yandex.ru`

### Commands

## Remote
List resources: `ls`
Change current collection: `cd my_coll`
Upload local file to current collection: `put /tmp/test.csv`
Show resource properties: `propget /my/res.txt`
Delete non-collection resource: `delete /my/res.txt`

## Local working directory
Show local working directory: `lpwd`
Change local working directory: `lcd ..`
List files in local working directory: `lls`

# zip and unzip CLI

## Help
Short help: `zip -h`
Full help: `zip -h2`

## List
List files in an archive: 
- `zipinfo my.zip`
- `unzip -l archive.zip`

## Unpack
Unpack to specific folder: `unzip package.zip -d /opt`
Unpack single file: `unzip my.zip path/to/entry.py`
Show content of specific file: `unzip -c my.zip my_folder/my_file.txt | less`
Test ZIP file: `unzip -t my.zip`

## Pack
Zip a file: `zip handler.zip handler.py`
Zip directory with "my_folder": `zip -r OUTPUT.zip my_folder`
Zip directory without "my_folder": `cd my_folder; zip -r ../OUTPUT.zip *`
Zip directory without compression: `zip -0 -r OUTPUT.zip my_folder`
Zip directory excluding log files: `zip -r out.zip my_folder -x *.log`

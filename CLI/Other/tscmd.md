# tscmd TagSpaces CLI

Docs: https://docs.tagspaces.org/dev/command-line-tools/

Install: 
1. TagSpaces: `npm install -global @tagspaces/shell`
2. Thumbnails generation: `npm i sharp -g`

Index a folder: `tscmd -m indexer /home/aleks/tmp/documents_recognition`

Generate thumbnails:
1. `export NODE_PATH=$(npm root --quiet -g)`
2. `tscmd -m thumbgen /home/aleks/tmp/documents_recognition`

Cleaning obsolete thumbnails: `tscmd -m metacleaner /home/aleks/tmp/documents_recognition`

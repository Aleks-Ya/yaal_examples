# gzip CLI

**For unpack an archive see `gunzip.md`**

Help: `gzip -h`

Compress a file:
- Delete the original file: `gzip data.txt` -> `data.txt.gz`
- Keep the original file: `gzip -k data.txt` -> `data.txt.gz`
Show content of an archive: `gzip -l data.txt.gz`

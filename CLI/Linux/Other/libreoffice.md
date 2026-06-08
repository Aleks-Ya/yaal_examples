# libreoffice CLI

## Commands
Help: `libreoffice --help`
Open a Draw file: `libreoffice --draw /path/to/file.odg`

### Convert FODG to ODF
Single file: `libreoffice --headless --convert-to odg draw.fodg`
Directory: 
```shell
find ~/DocsVault/LibreOfficeDraw -type f -name "*.fodg" \
	-execdir libreoffice --headless --convert-to odg {} \;
```

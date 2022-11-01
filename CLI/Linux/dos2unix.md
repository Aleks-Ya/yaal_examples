# dos2unix

Install: `sudo apt install -y dos2unix`

Convert current directory from Linux (CR) to Windows (CRLF): `find . -type f -print0 | xargs -0 unix2dos`
Convert current directory from Windows (CRLF) to Linux (CR): `find . -type f -print0 | xargs -0 dos2unix`

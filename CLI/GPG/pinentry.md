# pinentry CLI

Help: `pinentry --help`

Request a pin from user:
```shell
pinentry <<EOF
SETTITLE Test Pinentry
SETDESC This is a test of pinentry without gpg.
SETPROMPT Enter something:
GETPIN
BYE
EOF
```

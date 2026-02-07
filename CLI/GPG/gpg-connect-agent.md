# gpg-connect-agent CLI

Help: `gpg-connect-agent --help`

Request a passphrase from the user: `gpg-connect-agent 'GET_PASSPHRASE --data "desc" "prompt" "error" 0' /bye`
List keys: `gpg-connect-agent 'keyinfo --list' /bye`
Show key info: `gpg-connect-agent "KEYINFO 47589FEAB3EDBA343DDF3D2583DCB5300503A6A7" /bye`

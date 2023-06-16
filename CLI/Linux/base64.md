# base64 CLI

## Encode
Encode a string to Base64 (with default 76-sybmols line breaks): `echo -n 'my-string' | base64`
Encode a string to Base64 (without line breaks): `echo -n 'my-string' | base64 -w 0`
Encode a file to Base64: `base64 data.txt > data.b64`

## Decode
Decode Base64-string: `echo -n 'bXktc3RyaW5n' | base64 -d`
Decode a Base64 file to plain file: `base64 -d data.b64 > out2.txt`

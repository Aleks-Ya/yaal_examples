# base64 CLI

Encode a string to Base64: `echo -n 'my-string' | base64`
Decode Base64-string: `echo -n 'bXktc3RyaW5n' | base64 -d`
Encode a file to Base64: `base64 data.txt > data.b64`
Decode a Base64 file to plain file: `base64 -d data.b64 > out2.txt`

# sed CLI

Replace a sub-string:
```
echo "abcd" | sed 's/bc/xy/' #axyd
```
Match a number (character class "\d" is not supported):
```
#First match
echo "a1c4" | sed 's/[0-9]/7/' #a7c4
#All matches
echo "a1c4" | sed 's/[0-9]/7/g' #a7c7
```
Exact quantificator:
```
echo "babbc" | sed 's/b\{2\}/B/' #baBc
```
1 or more quantificator ("\+")
```
echo "baabbccbbb" | sed 's/b\+/B/g' #BaaBccB
```
Use group in replacer:
```
echo "7number" | sed 's/\([0-9]\)number/number\1/' #number7
```
URL in replacer:
```
echo "Visit {{URL}} for details" | sed 's/{{URL}}/http:\/\/localhost:8888/' #Visit http://localhost:8888 for details
```
Single quote in replacer:
```
echo "Replace 'a' also" | sed "s/'a'/'b'/" #Replace 'b' also
```
Delete lines matches a pattern from a file:
```
sed '/abc/d' input.txt
```
Dot in a character class:
```
echo "ab.c.d" | sed 's/[a.]/X/g' #XbXcXd
```
Delete part of a string:
```
echo "abcd" | sed 's/bc//' #ad
```
Convert all *.MOV files in a folder to MP4:
```
export FILES=/home/aleks/tmp/2021-07-20/*.MOV
for filename in $FILES; do ffmpeg -i "$filename" -c:v copy -c:a copy "$filename".mp4; done
```

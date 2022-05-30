# ffmpeg CLI

## Info
Show info about video:
```
ffprobe -hide_banner video.mp4
ffmpeg -hide_banner -i video.mp4
```
Show chapters: `ffprobe -hide_banner -show_chapters video.mp4`
List all filters: `ffmpeg -hide_banner -filters`

## Modify
Rotate a file horizontally: `ffmpeg -loglevel warning -i video.mp4 -vf "hflip" rotated.mp4`
Change video speed: 
```
# Twice slower
ffmpeg -loglevel warning -i origin.mp4 -filter:v setpts=2*PTS slow.mp4
# Twice faster
ffmpeg -loglevel warning -i origin.mp4 -filter:v setpts=0.5*PTS fast.mp4
```
Convert MTS file to MP4:
```
#Preserve audio:
ffmpeg -i input.MTS -c:v copy -c:a copy output.mp4
#Remove audio:
ffmpeg -i input.MTS -c:v copy -an output.mp4
ffmpeg -i input.MTS -vcodec copy -an output.mp4
```
Convert all *.MOV files in a folder to MP4:
```
export FILES=/home/aleks/tmp/2021-07-20/*.MOV
for filename in $FILES; do ffmpeg -i "$filename" -c:v copy -c:a copy "$filename".mp4; done
```

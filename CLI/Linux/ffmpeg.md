# ffmpeg CLI

## Info
Help: `ffmpeg -h`
Show info about video:
```
ffprobe -hide_banner video.mp4
ffmpeg -hide_banner -i video.mp4
```
Show chapters: `ffprobe -hide_banner -show_chapters video.mp4`
List all filters: `ffmpeg -hide_banner -filters`

## Modify video
### Rotate a file horizontally
`ffmpeg -loglevel warning -i video.mp4 -vf "hflip" rotated.mp4`
### Change video speed
```
# Twice slower
ffmpeg -loglevel warning -i origin.mp4 -filter:v setpts=2*PTS slow.mp4
# Twice faster
ffmpeg -loglevel warning -i origin.mp4 -filter:v setpts=0.5*PTS fast.mp4
```
### MTS to MP4
Convert MTS file to MP4:
```
#Preserve audio:
ffmpeg -i input.MTS -c:v copy -c:a copy output.mp4
#Remove audio:
ffmpeg -i input.MTS -c:v copy -an output.mp4
ffmpeg -i input.MTS -vcodec copy -an output.mp4
```
### MOV to MP4
Convert single file: `ffmpeg -i input.mov -c:v libx264 -c:a aac -vf format=yuv420p -movflags +faststart output.mp4`
Convert all MOV files in current directory recursively to MP4:
`find . -type f -iname "*.MOV" -exec bash -c 'ffmpeg -i "$0" -c:v libx264 -c:a aac -vf format=yuv420p -movflags +faststart "${0%.MOV}.mp4"' {} \;`
### Change FPS
Change FPS to 30: `ffmpeg -i in.mp4 -filter:v fps=fps=30 out.mp4`

## Modify audio
### AC3 to AAC
Convert MP4 with AC3 audio to MP4 with AAC audio: `ffmpeg -i input.mp4 -c:v copy -c:a aac output.mp4`
### M4A to MP3
Convert M4A to MP3: `ffmpeg -i music.m4a music.mp3`

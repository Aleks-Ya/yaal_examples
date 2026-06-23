# ffprobe CLI

Show info about video: `ffprobe -hide_banner video.mp4`
Show chapters: `ffprobe -hide_banner -show_chapters video.mp4`

Show distance between keyframes:
```shell
ffprobe -loglevel error -select_streams v:0 -skip_frame nokey \
  -show_entries frame=pts_time -of csv=print_section=0 \
  -read_intervals "%+60" my.MP4
```

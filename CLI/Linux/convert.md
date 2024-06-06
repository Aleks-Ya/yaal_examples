# convert

Install: `sudo apt install -y imagemagick`

## Docs
Help: `convert -help`
Geomery: http://www.imagemagick.org/script/command-line-processing.php#geometry

## Commands
Resize an image:
- Percents: `convert -resize 50% in.jpg out.jpg`
- Height and width: `convert -resize 100x600 in.jpg out.jpg`
- Give height and width by ration: `convert -resize x400 in.jpg out.jpg`
- Number of pixels: `convert -resize 10000@ in.jpg out.jpg`
Resize all files in a folder: `find . -maxdepth 1 -iname "*.jpg" | xargs -I{} basename {} | xargs -L1 -I{} convert -resize 30% "{}" "resized_{}"`

## Aliases
`alias picture_for_screen='convert -resize 500000@ '`
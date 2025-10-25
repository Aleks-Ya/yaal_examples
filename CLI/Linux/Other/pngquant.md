# pngquant

Install: `sudo apt install -y pngquant`

Help: `pngquant -h`
Compress file (out to new file): `pngquant -- image.png` -> `image-fs8.png`
Compress file (overwrite): `pngquant --ext .png --force -- image.png`  -> `image.png`
Compress files by patter: `pngquant --ext .png --force -- *.png`
Compress file with specific speed (1-slow, 11-fast): `pngquant --speed 1 -- image.png` -> `image-fs8.png`

What if compress file many times? Skip compressed files (size doesn't change)

# JpegOptim
Install: `sudo apt-get install jpegoptim`

Help: `jpegoptim -h`
Dry run: `jpegoptim -n original.jpg`
Output to another dir: `jpegoptim -m15 original.jpg -d /tmp/out`
Lossless compression (ovewrite original): `jpegoptim original.jpg`
Loss compression with 15 quality (ovewrite original): `jpegoptim -m15 original.jpg`
Process all files in a dir: `jpegoptim /tmp/*.jpg`

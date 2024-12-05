# xvfb CLI

Install: `sudo apt install xvfb`

## `Xvfb`
Help: `Xvfb -help`
Start new server with number 99: `Xvfb :99 -screen 0 1024x768x24`
Check server 99: `DISPLAY=:99 xdpyinfo`
Run test app in the server 99: `DISPLAY=:99 xclock`

## `xvfb-run` (run XVFB server and execute app in it)
Help: `xvfb-run --help`
Run Hello World: `xvfb-run -n 98 python hello_world.py`
List available framebuffers (opt 1): `ls /dev/fb*`
List available framebuffers (opt 1): `xrandr --listmonitors`
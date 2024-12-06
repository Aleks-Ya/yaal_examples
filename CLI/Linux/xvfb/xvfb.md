# xvfb CLI

Install: `sudo apt install xvfb`

## `Xvfb` (run new XVFB server)
Help: `Xvfb -help`
Start new server with number 99: `Xvfb :99 -screen 0 1024x768x24`
Check server 99: `DISPLAY=:99 xdpyinfo` or `xdpyinfo -display :99`
Run test app in the server 99: `DISPLAY=:99 xclock`

## `xvfb-run` (run new XVFB server and execute app in it)
Help: `xvfb-run --help`
Run XClock app: `xvfb-run -n 98 xclock`
Run PyQt6 app: `xvfb-run -n 98 python hello_pyqt6.py --platform offscreen`
List available framebuffers (opt 1): `ls /dev/fb*`
List available framebuffers (opt 1): `xrandr --listmonitors`

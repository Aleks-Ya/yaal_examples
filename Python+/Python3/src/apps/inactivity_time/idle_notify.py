#!/usr/bin/env python
from argparse import ArgumentParser, Namespace
import time
import subprocess

from gi.repository import Gio, GLib


# Run: ~/pr/home/yaal_examples/Python+/Python3/src/apps/inactivity_time/idle_notify.py
# Run: ~/pr/home/yaal_examples/Python+/Python3/src/apps/inactivity_time/idle_notify.py --idle-seconds 30

def get_idle_ms() -> int:
    """
    Reads idle time (ms) from GNOME Mutter IdleMonitor over the session bus.
    Works on GNOME Wayland.
    """
    bus = Gio.bus_get_sync(Gio.BusType.SESSION, None)

    # org.gnome.Mutter.IdleMonitor API
    # Object path: /org/gnome/Mutter/IdleMonitor/Core
    # Interface:   org.gnome.Mutter.IdleMonitor
    proxy = Gio.DBusProxy.new_sync(
        bus,
        Gio.DBusProxyFlags.NONE,
        None,
        "org.gnome.Mutter.IdleMonitor",
        "/org/gnome/Mutter/IdleMonitor/Core",
        "org.gnome.Mutter.IdleMonitor",
        None,
    )
    # Method: GetIdletime() -> u (milliseconds)
    result = proxy.call_sync("GetIdletime", None, Gio.DBusCallFlags.NONE, -1, None, )
    return int(result.unpack()[0])


def notify() -> None:
    subprocess.run(["paplay", "/usr/share/sounds/freedesktop/stereo/bell.oga"], check=False)
    subprocess.run(
        ["yad", "--picture", "--fullscreen", "--undecorated", "--size=orig",
         "--filename=/home/aleks/Pictures/Focus2.png"],
        check=False
    )


def parse_args() -> Namespace:
    parser: ArgumentParser = ArgumentParser(description="Monitor user idle time and notify after inactivity.")
    parser.add_argument(
        "--idle-seconds",
        type=int,
        default=60,
        help="Number of seconds of inactivity before notification (default: 60)",
    )
    args: Namespace = parser.parse_args()
    return args


def main():
    args: Namespace = parse_args()
    idle_seconds: int = args.idle_seconds
    print(f"Idle period: {idle_seconds} sec")
    poll_seconds: int = 2
    already_notified: bool = False
    while True:
        idle_ms: int = get_idle_ms()
        idle_s: float = idle_ms / 1000.0
        if idle_s >= idle_seconds:
            if not already_notified:
                notify()
                already_notified = True
        else:
            # user became active again -> re-arm
            already_notified = False
        time.sleep(poll_seconds)


if __name__ == "__main__":
    main()

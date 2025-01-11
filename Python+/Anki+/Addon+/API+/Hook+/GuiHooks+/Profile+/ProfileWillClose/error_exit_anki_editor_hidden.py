from time import sleep

from aqt import gui_hooks

# Steps to reproduce:
# 1. Open Browser
# 2. Select 2 or more notes (the Editor will be hidden)
# 3. Close Anki by choosing "Close" on the OS bar
# 4. See the error message
gui_hooks.profile_will_close.append(lambda: sleep(3))

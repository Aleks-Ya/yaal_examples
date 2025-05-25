# Anki add-on.
# 1. Adds "Clean generated fields" item in "Main menu"/"Tools".
# 2. On click, perform find by regex and replace in specified note fields.
from aqt import gui_hooks

from .profile_did_open_hook import ProfileDidOpenHook

dry_run: bool = False
profile_did_open: ProfileDidOpenHook = ProfileDidOpenHook(dry_run)
gui_hooks.profile_did_open.append(profile_did_open)

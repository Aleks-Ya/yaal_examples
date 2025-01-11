from aqt import gui_hooks
from aqt.utils import show_info


def __on_event():
    show_info("Profile will close")


gui_hooks.profile_will_close.append(__on_event)

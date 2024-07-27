from aqt import gui_hooks
from aqt.utils import showInfo

from ._common.disable import enabled


def __on_event():
    showInfo("Profile will close")


if enabled():
    gui_hooks.profile_will_close.append(__on_event)

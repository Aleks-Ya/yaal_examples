from aqt import gui_hooks
from aqt.reviewer import Reviewer
from aqt.utils import show_info

from ._common.disable import enabled


def __on_event(reviewer: Reviewer) -> None:
    show_info(f"Reviewer: {reviewer}")


if enabled():
    gui_hooks.reviewer_did_init.append(__on_event)

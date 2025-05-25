from aqt import gui_hooks, QMenu, qconnect, QAction, mw
from aqt.reviewer import Reviewer
from aqt.utils import show_info

from ._common.disable import enabled


def __show_message():
    show_info('You clicked it')


def __on_event(_: Reviewer, menu: QMenu) -> None:
    action1: QAction = QAction("Custom menu item 1", mw)
    qconnect(action1.triggered, __show_message)
    menu.addAction(action1)


if enabled():
    gui_hooks.reviewer_will_show_context_menu.append(__on_event)

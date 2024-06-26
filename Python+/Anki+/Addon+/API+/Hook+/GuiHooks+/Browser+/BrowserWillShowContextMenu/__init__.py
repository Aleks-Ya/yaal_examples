from PyQt6.QtGui import QAction
from PyQt6.QtWidgets import QMenu
from aqt import gui_hooks, qconnect
from aqt.browser import Browser
from aqt.utils import showInfo

from ._common.disable import enabled


def __on_click():
    showInfo('You clicked the Browser context menu item')


def __on_event(browser: Browser, menu: QMenu) -> None:
    action: QAction = QAction("My menu item 1", browser)
    qconnect(action.triggered, __on_click)
    menu.addAction(action)


if enabled():
    gui_hooks.browser_will_show_context_menu.append(__on_event)

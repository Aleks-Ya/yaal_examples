from logging import Logger

from aqt import gui_hooks, QMessageBox
from aqt.utils import showInfo

from ._common import menu
from ._common.disable import enabled
from ._common.log import get_addon_logger

log: Logger = get_addon_logger()

if enabled():
    showInfo("Hello world")

if enabled():
    showInfo(text="Message with custom title", title="My title")

if enabled():
    showInfo(text="Warning message", type="warning")

if enabled():
    showInfo(text="Critical message", type="critical")

if enabled():
    def __show():
        showInfo(text="Hello world", help="browsing.html")  # help will be appended to "https://docs.ankiweb.net/"


    def __add_menu_item():
        menu.add_mw_menu_item("Show Info: help", __show)


    gui_hooks.main_window_did_init.append(__add_menu_item)

if enabled():
    clicked_button: int = showInfo("Message",
                                   customBtns=[QMessageBox.StandardButton.Ok, QMessageBox.StandardButton.Cancel])
    if clicked_button == QMessageBox.StandardButton.Ok:
        log.info("Ok was clicked")
    if clicked_button == QMessageBox.StandardButton.Cancel:
        log.info("Cancel was clicked")

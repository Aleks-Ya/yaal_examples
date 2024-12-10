from logging import Logger

from aqt import gui_hooks, QMessageBox
from aqt.utils import show_info

from ._common import menu
from ._common.disable import enabled
from ._common.log import get_addon_logger

log: Logger = get_addon_logger()

if enabled():
    show_info("Hello world")

if enabled():
    show_info(text="Message with custom title", title="My title")

if enabled():
    show_info(text="Warning message", type="warning")

if enabled():
    show_info(text="Critical message", type="critical")

if enabled():
    def __show():
        show_info(text="Hello world", help="browsing.html")  # help will be appended to "https://docs.ankiweb.net/"


    def __add_menu_item():
        menu.add_mw_menu_item("Show Info: help", __show)


    gui_hooks.main_window_did_init.append(__add_menu_item)

if enabled():
    clicked_button: QMessageBox = show_info("Message",
                                            customBtns=[QMessageBox.StandardButton.Ok,
                                                        QMessageBox.StandardButton.Cancel])
    if clicked_button == QMessageBox.StandardButton.Ok:
        log.info("Ok was clicked")
    if clicked_button == QMessageBox.StandardButton.Cancel:
        log.info("Cancel was clicked")

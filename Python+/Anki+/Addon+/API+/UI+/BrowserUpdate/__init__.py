from logging import Logger
from typing import Optional

from aqt import gui_hooks, mw
from aqt.browser import Browser

from ._common.log import get_addon_logger
from ._common.disable import enabled
from ._common import menu

log: Logger = get_addon_logger()


def __update_browser():
    browser: Optional[Browser] = __get_browser()
    if browser:
        browser.update()
        log.info("Browser was updated")
    else:
        log.info("Browser is None")


def __get_browser() -> Optional[Browser]:
    for widget in mw.app.allWidgets():
        if isinstance(widget, Browser):
            return widget
    return None


def __add_menu_item():
    menu.add_mw_menu_item("Update Browser", __update_browser)


if enabled():
    gui_hooks.main_window_did_init.append(__add_menu_item)

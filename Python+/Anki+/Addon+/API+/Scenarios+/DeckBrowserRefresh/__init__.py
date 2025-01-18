from logging import Logger

from aqt import gui_hooks, mw

from ._common.log import get_addon_logger
from ._common.disable import enabled
from ._common import menu

log: Logger = get_addon_logger()


def __refresh_deck_browser():
    mw.deckBrowser.refresh()
    log.info("DeckBrowser was refreshed")


def __add_menu_item():
    menu.add_mw_menu_item("Refresh DeckBrowser", __refresh_deck_browser)


if enabled():
    gui_hooks.main_window_did_init.append(__add_menu_item)

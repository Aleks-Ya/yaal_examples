from logging import Logger

from anki.collection import Collection
from aqt.utils import show_info

from ._common.log import get_addon_logger
from ._common.disable import enabled

log: Logger = get_addon_logger()


def __log(message: str) -> None:
    log.info(message)
    show_info(message, title="SwitchProfile")


def __on_profile_did_open():
    __log(f"Profile did open: {mw.pm.name}")


def __on_profile_will_close():
    __log(f"Profile will close: {mw.pm.name}")


def __on_collection_did_load(col: Collection):
    __log(f"Collection did load: col={col}, profile={mw.pm.name}")


def __on_collection_did_temporarily_close(col: Collection):
    __log(f"Collection did temporarily close: {col}")


def __on_collection_will_temporarily_close(col: Collection):
    __log(f"Collection will temporarily close: {col}")


if enabled():
    setup_message: str = "Setting hooks up"
    log.info(setup_message)
    show_info(setup_message)

    from aqt import gui_hooks, mw

    gui_hooks.collection_did_load.append(__on_collection_did_load)
    gui_hooks.collection_will_temporarily_close.append(__on_collection_will_temporarily_close)
    gui_hooks.collection_did_temporarily_close.append(__on_collection_did_temporarily_close)
    gui_hooks.profile_did_open.append(__on_profile_did_open)
    gui_hooks.profile_will_close.append(__on_profile_will_close)

from logging import Logger

from aqt import gui_hooks

from ._common.disable import enabled
from ._common.log import get_addon_logger

log: Logger = get_addon_logger()


def __on_event() -> None:
    log.info(f"Theme has changed!")


if enabled(True):
    gui_hooks.theme_did_change.append(__on_event)

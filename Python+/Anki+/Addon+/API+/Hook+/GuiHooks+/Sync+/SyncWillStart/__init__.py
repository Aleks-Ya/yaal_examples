from logging import Logger

from aqt import gui_hooks
from aqt.utils import showInfo

from ._common.disable import enabled
from ._common.log import get_addon_logger

log: Logger = get_addon_logger()


def __on_event() -> None:
    log.info(f"Sync will start")
    showInfo(f"Sync will start")


if enabled():
    gui_hooks.sync_will_start.append(__on_event)

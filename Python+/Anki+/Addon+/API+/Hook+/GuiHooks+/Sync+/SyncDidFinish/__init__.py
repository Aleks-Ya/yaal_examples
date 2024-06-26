from logging import Logger

from aqt import gui_hooks
from aqt.utils import showInfo

from ._common.disable import enabled
from ._common.log import get_addon_logger

log: Logger = get_addon_logger()


def __on_event() -> None:
    log.info(f"Sync did finished (collection only)")
    showInfo(f"Sync did finished (collection only)")


if enabled():
    gui_hooks.sync_did_finish.append(__on_event)

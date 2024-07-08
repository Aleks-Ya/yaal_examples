from logging import Logger

from aqt import gui_hooks
from aqt.utils import showInfo

from ._common.disable import enabled
from ._common.log import get_addon_logger

log: Logger = get_addon_logger()


def __on_event(entry: str) -> None:
    log.info(f"MediaSyncDidProgress: entry={entry}")
    showInfo(f"MediaSyncDidProgress: entry={entry}")


if enabled():
    gui_hooks.media_sync_did_progress.append(__on_event)

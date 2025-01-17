from logging import Logger

from aqt import gui_hooks
from aqt.utils import show_info

from ._common.disable import enabled
from ._common.log import get_addon_logger

log: Logger = get_addon_logger()


def __on_event(running: bool) -> None:
    log.info(f"MediaSyncDidStartOrStop: sync is running: {running}")
    show_info(f"MediaSyncDidStartOrStop: sync is running: {running}")


if enabled():
    gui_hooks.media_sync_did_start_or_stop.append(__on_event)

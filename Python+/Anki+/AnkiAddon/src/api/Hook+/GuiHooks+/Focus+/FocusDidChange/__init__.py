from logging import Logger
from typing import Optional

from aqt.qt import QWidget
from aqt import gui_hooks

from ._common.disable import enabled
from ._common.log import get_addon_logger

log: Logger = get_addon_logger()


def __on_event(new: Optional[QWidget], old: Optional[QWidget]) -> None:
    log.info(f"focus_did_change: old={old}, new={new}")


if enabled():
    gui_hooks.focus_did_change.append(__on_event)

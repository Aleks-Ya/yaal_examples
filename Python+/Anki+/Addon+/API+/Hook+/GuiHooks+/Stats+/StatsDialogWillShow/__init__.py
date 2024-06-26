from logging import Logger

from aqt import gui_hooks
from aqt.stats import NewDeckStats
from aqt.utils import showInfo

from ._common.disable import enabled
from ._common.log import get_addon_logger

log: Logger = get_addon_logger()


def __on_event(dialog: NewDeckStats):
    log.info(f"dialog.name: {dialog.name}")
    log.info(f"dialog.form: {dialog.form}")
    showInfo(f"Stats dialog will show: {dialog.name}")


if enabled():
    gui_hooks.stats_dialog_will_show.append(__on_event)

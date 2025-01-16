from logging import Logger

from aqt import gui_hooks
from aqt.editor import Editor
from aqt.utils import show_info

from ._common.disable import enabled
from ._common.log import get_addon_logger

log: Logger = get_addon_logger()


def __on_event(shortcuts: list[tuple], editor: Editor):
    message: str = f"Editor did init shortcuts: shortcuts={shortcuts}, editor={editor}"
    log.info(message)
    show_info(message)


if enabled():
    gui_hooks.editor_did_init_shortcuts.append(__on_event)

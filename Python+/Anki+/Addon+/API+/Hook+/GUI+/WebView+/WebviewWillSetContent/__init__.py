from logging import Logger
from typing import Optional

from aqt import gui_hooks
from aqt.webview import WebContent

from ._common.disable import enabled
from ._common.log import get_addon_logger

log: Logger = get_addon_logger()


def _on_event(web_content: WebContent, context: Optional[object]) -> None:
    log.info(f"\n\nwebview_will_set_content:\nmessage={web_content}\ncontext={context}")


if enabled():
    gui_hooks.webview_will_set_content.append(_on_event)

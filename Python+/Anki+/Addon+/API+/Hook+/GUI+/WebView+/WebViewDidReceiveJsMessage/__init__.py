from typing import Any

from aqt import gui_hooks, mw
from aqt.utils import showInfo

from ._common.disable import enabled
from ._common import menu

_my_python_action: str = "my-action"


def _on_event(handled: tuple[bool, Any], message: str, context: Any) -> tuple[bool, Any]:
    showInfo(f"did_receive_js_message: message={message}")
    if message == _my_python_action:
        showInfo(f"Received my event: message={message}")
        return True, None
    return handled


def _menu_item_action():
    showInfo('You clicked "Send pycmd event"')
    mw.web.eval(f"pycmd('{_my_python_action}')")


if enabled():
    gui_hooks.webview_did_receive_js_message.append(_on_event)
    menu.add_mw_menu_item("Send pycmd event", _menu_item_action)

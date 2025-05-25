from typing import Any

from aqt import gui_hooks, mw
from aqt.utils import show_info

from ._common.disable import enabled
from ._common import menu

__my_python_action: str = "my-action"


def __on_event(handled: tuple[bool, Any], message: str, _: Any) -> tuple[bool, Any]:
    show_info(f"did_receive_js_message: message={message}")
    if message == __my_python_action:
        show_info(f"Received my event: message={message}")
        return True, None
    return handled


def __menu_item_action() -> None:
    show_info('You clicked "Send pycmd event"')
    mw.web.eval(f"pycmd('{__my_python_action}')")


if enabled():
    gui_hooks.webview_did_receive_js_message.append(__on_event)
    menu.add_mw_menu_item("Send pycmd event", __menu_item_action)

from logging import Logger
from typing import Any

from aqt import gui_hooks
from aqt.webview import AnkiWebView, AnkiWebViewKind

from ._common.disable import enabled
from ._common.log import get_addon_logger

log: Logger = get_addon_logger()


def __on_event(webview: AnkiWebView) -> None:
    if webview.kind != AnkiWebViewKind.BROWSER_CARD_INFO:
        return
    log.info(f"webview_did_inject_style_into_page: {webview}")


def __callback(val: Any):
    log.info(f"Callback:\n{val}")


def __print_outer_html(webview: AnkiWebView) -> None:
    webview.evalWithCallback("document.documentElement.outerHTML", __callback)


if enabled():
    gui_hooks.webview_did_inject_style_into_page.append(__on_event)

if enabled():
    gui_hooks.webview_did_inject_style_into_page.append(__print_outer_html)

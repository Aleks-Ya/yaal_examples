from logging import Logger
from typing import Optional

from aqt import gui_hooks, mw
from aqt.webview import WebContent

from ._common.disable import enabled
from ._common.log import get_addon_logger

log: Logger = get_addon_logger()


def __on_event(web_content: WebContent, context: Optional[object]) -> None:
    log.info(f"\n\nWebviewWillSetContent:\nmessage={web_content}\ncontext={context}")


def __add_custom_css(web_content: WebContent, context: Optional[object]) -> None:
    log.info(f"\n\nWebviewWillSetContent:\nmessage={web_content}\ncontext={context}")
    addon_package = mw.addonManager.addonFromModule(__name__)
    web_content.css.append(f"/_addons/{addon_package}/web/custom.css")
    # https://addon-docs.ankiweb.net/hooks-and-filters.html?highlight=exports#managing-external-resources-in-webviews


if enabled():
    gui_hooks.webview_will_set_content.append(__on_event)

if enabled():
    mw.addonManager.setWebExports(__name__, r"web/.*(css|js)")
    gui_hooks.webview_will_set_content.append(__add_custom_css)

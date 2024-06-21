import datetime
from logging import Logger

from aqt import gui_hooks
from aqt.deckbrowser import DeckBrowser, DeckBrowserContent

from ._common.disable import enabled
from ._common.log import get_addon_logger

log: Logger = get_addon_logger()


def __on_action(deck_browser: DeckBrowser, content: DeckBrowserContent):
    log.info(f"DeckBrowser: {deck_browser}\n\n")
    log.info(f"DeckBrowserContent tree: {content.tree}\n\n")
    log.info(f"DeckBrowserContent stats: {content.stats}\n\n")

    on_click_js: str = "document.getElementById('updated').textContent=new Date().toLocaleString()"
    button: str = f"""<button onclick="{on_click_js}">Refresh</button>"""
    content.stats += ("\n<div>Custom text in statistics. "
                      f"Updated: <span id='updated'>{datetime.datetime.now().replace(microsecond=0)}</span>"
                      f"{button}</div>")

    log.info(f"DeckBrowserContent stats (edited): {content.stats}\n\n")


if enabled():
    gui_hooks.deck_browser_will_render_content.append(__on_action)

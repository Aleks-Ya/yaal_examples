import datetime
from logging import Logger

from aqt import gui_hooks
from aqt.addons import AddonManager
from aqt.deckbrowser import DeckBrowser, DeckBrowserContent

from ._common.disable import enabled


def _on_action(deck_browser: DeckBrowser, content: DeckBrowserContent):
    # Output: ~/.local/share/Anki2/logs/addons/Hook-GUI-DeckBrowser-DeckBrowserWillRenderContent/Hook-GUI-DeckBrowser-DeckBrowserWillRenderContent.log
    log: Logger = AddonManager.get_logger(__name__)
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
    gui_hooks.deck_browser_will_render_content.append(_on_action)

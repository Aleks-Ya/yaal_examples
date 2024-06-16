from aqt import gui_hooks
from aqt.deckbrowser import DeckBrowser
from aqt.utils import showInfo

from ._common.disable import enabled


def _on_action(deck_browser: DeckBrowser):
    showInfo(f'Deck browser did render: {deck_browser}')


if enabled():
    gui_hooks.deck_browser_did_render.append(_on_action)

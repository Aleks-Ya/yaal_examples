from aqt import gui_hooks
from aqt.deckbrowser import DeckBrowser
from aqt.utils import showInfo


def on_action(deck_browser: DeckBrowser):
    showInfo(f'Deck browser did render: {deck_browser}')


# gui_hooks.deck_browser_did_render.append(on_action)

from aqt import gui_hooks
from aqt.deckbrowser import DeckBrowser
from aqt.utils import show_info

from ._common.disable import enabled


def __on_action(deck_browser: DeckBrowser):
    show_info(f'Deck browser did render: {deck_browser}')


if enabled():
    gui_hooks.deck_browser_did_render.append(__on_action)

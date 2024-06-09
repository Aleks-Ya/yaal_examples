from PyQt6.QtWidgets import QMenu
from aqt import gui_hooks
from aqt.utils import showInfo


def on_action(menu: QMenu, deck_id: int):
    showInfo(f'Deck browser will render content: deck_id={deck_id}, menu={menu}')


# gui_hooks.deck_browser_will_render_content.append(on_action)

from typing import Sequence

from PyQt6.QtGui import QAction
from anki.notes import NoteId
from aqt import gui_hooks, qconnect
from aqt.browser import Browser
from aqt.utils import showInfo


def show_selected_notes(browser: Browser):
    selected_note_ids: Sequence[NoteId] = browser.selected_notes()
    selected_card_ids: Sequence[int] = browser.selected_cards()
    showInfo(f'''
        Selected {len(selected_note_ids)} notes.
        Selected {len(selected_card_ids)} cards.
    ''')


def add_item_to_edit_menu(browser: Browser) -> None:
    action: QAction = QAction("Info about selected notes", browser)
    qconnect(action.triggered, lambda: show_selected_notes(browser))
    browser.form.menuEdit.addAction(action)


gui_hooks.browser_will_show.append(add_item_to_edit_menu)

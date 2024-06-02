from typing import Sequence

from anki.notes import NoteId
from aqt.browser import Browser
from aqt.utils import showInfo

from ._common import menu


def show_selected_notes(browser: Browser):
    selected_note_ids: Sequence[NoteId] = browser.selected_notes()
    selected_card_ids: Sequence[int] = browser.selected_cards()
    showInfo(f'''
        Selected {len(selected_note_ids)} notes.
        Selected {len(selected_card_ids)} cards.
    ''')


menu.add_browser_menu("Info about selected notes", show_selected_notes)

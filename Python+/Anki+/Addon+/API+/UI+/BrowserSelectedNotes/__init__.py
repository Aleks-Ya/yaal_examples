from typing import Sequence

from anki.notes import NoteId
from aqt.browser import Browser
from aqt.utils import show_info

from ._common.disable import enabled
from ._common import menu


def __show_selected_notes(browser: Browser):
    selected_note_ids: Sequence[NoteId] = browser.selected_notes()
    selected_card_ids: Sequence[int] = browser.selected_cards()
    show_info(f'''
        Selected {len(selected_note_ids)} notes.
        Selected {len(selected_card_ids)} cards.
    ''')


if enabled():
    menu.add_browser_menu("Info about selected notes", __show_selected_notes)

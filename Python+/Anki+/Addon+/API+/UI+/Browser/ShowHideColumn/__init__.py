from anki.collection import Collection
from aqt.browser import Browser, Table
from aqt.utils import show_info

from ._common.disable import enabled
from ._common import menu

__due_column: str = "cardDue"


def __on_show_due_column(browser: Browser):
    col: Collection = browser.col
    table: Table = browser.table
    if table.is_notes_mode():
        if __due_column not in col.load_browser_note_columns():
            table._model.toggle_column(__due_column)
    else:
        if __due_column not in col.load_browser_card_columns():
            table._model.toggle_column(__due_column)


def __on_hide_due_column(browser: Browser):
    col: Collection = browser.col
    table: Table = browser.table
    if table.is_notes_mode():
        if __due_column in col.load_browser_note_columns():
            table._model.toggle_column(__due_column)
    else:
        if __due_column in col.load_browser_card_columns():
            table._model.toggle_column(__due_column)


def __on_list_column_names(browser: Browser):
    note_columns: list[str] = browser.col.load_browser_note_columns()
    card_columns: list[str] = browser.col.load_browser_card_columns()
    show_info(f"Note columns: {note_columns}\nCard columns: {card_columns}")


if enabled():
    menu.add_browser_menu("List column names", __on_list_column_names)
    menu.add_browser_menu("Show column Due", __on_show_due_column)
    menu.add_browser_menu("Hide column Due", __on_hide_due_column)

from typing import Sequence

from anki.cards import Card
from anki.collection import BrowserColumns
from anki.notes import Note
from aqt import gui_hooks, mw
from aqt.browser import Column, Cell
from aqt.browser import ItemId, CellRow
from aqt.editor import Editor
from aqt.utils import showInfo

from .note_size import NoteSize

column_key = "note-size"


def add_custom_column(columns: dict[str, Column]) -> None:
    column_label = "Size"
    columns[column_key] = Column(
        key=column_key,
        cards_mode_label=column_label,
        notes_mode_label=column_label,
        sorting_cards=BrowserColumns.SORTING_ASCENDING,
        sorting_notes=BrowserColumns.SORTING_ASCENDING,
        uses_cell_font=True,
        alignment=BrowserColumns.ALIGNMENT_START,
        cards_mode_tooltip="",
        notes_mode_tooltip="",
    )


def modify_row(card_or_note_id: ItemId, is_note: bool, row: CellRow, columns: Sequence[str]) -> None:
    column_index: int = columns.index(column_key)
    cell: Cell = row.cells[column_index]
    if is_note:
        note: Note = mw.col.get_note(card_or_note_id)
    else:
        card: Card = mw.col.get_card(card_or_note_id)
        note: Note = card.note()
    size: int = NoteSize.calculate_note_size(note)
    cell.text = NoteSize.bytes_to_human_str(size)


gui_hooks.browser_did_fetch_columns.append(add_custom_column)
gui_hooks.browser_did_fetch_row.append(modify_row)


def on_button_click(editor: Editor):
    if editor.note:
        total_size: str = NoteSize.bytes_to_human_str(NoteSize.calculate_note_size(editor.note))
        total_texts_size: str = NoteSize.bytes_to_human_str(NoteSize.total_text_size(editor.note))
        total_files_size: str = NoteSize.bytes_to_human_str(NoteSize.total_file_size(editor.note))
        file_sizes: dict[str, int] = NoteSize.sort_by_size_desc(NoteSize.file_sizes(editor.note))
        files_sizes_str: list[str] = NoteSize.file_sizes_to_human_strings(file_sizes)
        files_str: str = '</li><li style="white-space:nowrap">'.join(files_sizes_str) \
            if len(files_sizes_str) > 0 else "<no-files>"
        detailed: str = f"""
                        <h3>Total note size: {total_size}</h3>
                        <ul>
                            <li>Texts size: {total_texts_size}</li>
                            <li>Files size: {total_files_size}</li>
                            <li>Files (big to small):</li>
                                <ol>
                                    <li style="white-space:nowrap">{files_str}</li>
                                </ol>
                        </ul>
                        """
        showInfo(detailed)


def add_editor_button(buttons: list[str], editor: Editor):
    button: str = editor.addButton(id="size_button", label="Size: -", icon=None, cmd="size_button_cmd",
                                   func=on_button_click, tip="Click to see details", disables=False)
    buttons.append(button)


def on_load_note(editor: Editor):
    size: str = NoteSize.bytes_to_human_str(NoteSize.calculate_note_size(editor.note)) if editor.note else "-"
    editor.web.eval(f"document.getElementById('size_button').textContent = 'Size: {size}'")


gui_hooks.editor_did_init_buttons.append(add_editor_button)
gui_hooks.editor_did_load_note.append(on_load_note)

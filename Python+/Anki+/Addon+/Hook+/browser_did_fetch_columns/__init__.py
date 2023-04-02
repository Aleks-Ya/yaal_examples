# Add a column into Browser window.
from anki.collection import BrowserColumns
from aqt import gui_hooks
from aqt.browser import Column


def add_custom_column(columns: dict[str, Column]) -> None:
    column_key = "mycustomcolumn"
    column_label = "The custom column"
    columns[column_key] = Column(
        key=column_key,
        cards_mode_label=column_label,
        notes_mode_label=column_label,
        sorting=BrowserColumns.SORTING_ASCENDING,
        uses_cell_font=True,
        alignment=BrowserColumns.ALIGNMENT_START,
        cards_mode_tooltip="",
        notes_mode_tooltip="",
    )


gui_hooks.browser_did_fetch_columns.append(add_custom_column)

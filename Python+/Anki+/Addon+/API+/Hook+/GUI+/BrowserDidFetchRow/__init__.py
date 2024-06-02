# Makes 1st column text upper-case.
from typing import Sequence

from aqt import gui_hooks
from aqt.browser import ItemId, CellRow


def modify_row(card_or_note_id: ItemId, is_note: bool, row: CellRow, columns: Sequence[str]) -> None:
    column_index = 0
    cell = row.cells[column_index]
    # cell.text = cell.text.upper()


gui_hooks.browser_did_fetch_row.append(modify_row)

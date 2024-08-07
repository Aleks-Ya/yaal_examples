from logging import Logger
from typing import Sequence

from aqt import gui_hooks
from aqt.browser import ItemId, CellRow

from ._common.disable import enabled
from ._common.log import get_addon_logger

log: Logger = get_addon_logger()


def __on_event(card_or_note_id: ItemId, is_note: bool, row: CellRow, columns: Sequence[str]) -> None:
    log.info(f"BrowserDidFetchRow: card_or_note_id={card_or_note_id}, is_note={is_note}, cells={row.cells}, "
             f"font_name={row.font_name}, columns={columns}")
    column_index = 0
    cell = row.cells[column_index]
    cell.text = cell.text.upper()


if enabled():
    gui_hooks.browser_did_fetch_row.append(__on_event)

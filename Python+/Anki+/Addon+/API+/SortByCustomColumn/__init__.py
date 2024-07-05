from __future__ import annotations

from logging import Logger
from typing import Sequence

from anki.collection import BrowserColumns
from anki.notes import NoteId
from aqt import gui_hooks, mw
from aqt.browser import CellRow, Column, SearchContext
from aqt.browser.table import ItemId, Cell

from ._common.disable import enabled
from ._common.log import get_addon_logger

log: Logger = get_addon_logger()

__COLUMN_KEY = "custom-sortable-column"
__COLUMN_LABEL = "Custom Sortable Column"


def __add_browser_column(columns: dict[str, Column]) -> None:
    log.info(f"BrowserDidFetchColumns: columns={columns}")
    columns[__COLUMN_KEY] = Column(
        key=__COLUMN_KEY,
        cards_mode_label=__COLUMN_LABEL,
        notes_mode_label=__COLUMN_LABEL,
        sorting_cards=BrowserColumns.SORTING_ASCENDING,
        sorting_notes=BrowserColumns.SORTING_ASCENDING,
        uses_cell_font=True,
        alignment=BrowserColumns.ALIGNMENT_START,
        cards_mode_tooltip="",
        notes_mode_tooltip="",
    )


def __on_search(context: SearchContext) -> None:
    log.info(f"BrowserWillSearch: context={context}")
    if isinstance(context.order, Column) and context.order.key == __COLUMN_KEY:
        sort_col = mw.col.get_browser_column("noteFld")
        sort_col.notes_mode_label = __COLUMN_LABEL
        context.order = sort_col


def __on_browser_did_fetch_row(card_or_note_id: ItemId, is_note: bool, row: CellRow, columns: Sequence[str]) -> None:
    log.info(f"BrowserDidFetchRow: context={card_or_note_id}, is_note={is_note}, row={row.cells}, columns={columns}")
    if __COLUMN_KEY in columns:
        column_index: int = columns.index(__COLUMN_KEY)
        cell: Cell = row.cells[column_index]
        cell.text = __get_item_id_last_digits(card_or_note_id)


def __on_browser_did_search(context: SearchContext) -> None:
    log.info(f"BrowserDidSearch: context={context}")
    if context.ids and isinstance(context.order, Column) and context.order.notes_mode_label == __COLUMN_LABEL:
        context.ids = __sort_item_ids(context.ids)


def __sort_item_ids(item_ids: Sequence[ItemId]) -> Sequence[NoteId]:
    return sorted(item_ids, key=__get_item_id_last_digits, reverse=True)


def __get_item_id_last_digits(item_id: ItemId) -> int:
    return item_id % 1000


if enabled(True):
    gui_hooks.browser_did_fetch_columns.append(__add_browser_column)
    gui_hooks.browser_will_search.append(__on_search)
    gui_hooks.browser_did_search.append(__on_browser_did_search)
    gui_hooks.browser_did_fetch_row.append(__on_browser_did_fetch_row)

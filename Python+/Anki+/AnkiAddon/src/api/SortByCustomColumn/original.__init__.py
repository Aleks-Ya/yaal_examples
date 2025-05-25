from __future__ import annotations

from typing import Sequence, cast

from anki.collection import BrowserColumns
from anki.notes import NoteId
from aqt import gui_hooks, mw
from aqt.browser import CellRow, Column, SearchContext
from aqt.browser.table import ItemId

from ._common.disable import enabled

__COLUMN_KEY = "alphabeticSortField"
__COLUMN_LABEL = "Sort Field (Alphabetic)"


def __add_browser_column(columns: dict[str, Column]) -> None:
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
    if isinstance(context.order, Column) and context.order.key == __COLUMN_KEY:
        sort_col = mw.col.get_browser_column("noteFld")
        sort_col.notes_mode_label = __COLUMN_LABEL
        context.order = sort_col


def __on_browser_did_fetch_row(
        card_or_note_id: ItemId, is_note: bool, row: CellRow, columns: Sequence[str]) -> None:
    try:
        idx = columns.index(__COLUMN_KEY)
    except ValueError:
        return
    try:
        sort_field_col_idx = columns.index("noteFld")
    except ValueError:
        sort_field_col_idx = -1
    if sort_field_col_idx >= 0:
        row.cells[idx].text = row.cells[sort_field_col_idx].text
        row.cells[idx].is_rtl = row.cells[sort_field_col_idx].is_rtl
    else:
        if is_note:
            nid = cast(NoteId, card_or_note_id)
        else:
            nid = mw.col.db.scalar(
                "select nid from cards where id = ?", card_or_note_id
            )
        sfld = mw.col.db.scalar("select sfld from notes where id = ?", nid)
        row.cells[idx].text = sfld
        mid = mw.col.get_note(nid).mid
        notetype = mw.col.models.get(mid)
        sortf = notetype["sortf"]
        row.cells[idx].is_rtl = notetype["flds"][sortf]["rtl"]


def __unicode_sort(item_id: ItemId) -> str:
    sfld = mw.col.db.scalar("select sfld from notes where id = ?", item_id)
    if not sfld:
        sfld = mw.col.db.scalar(
            "select sfld from notes where id in (select nid from cards where id = ? limit 1)",
            item_id,
        )
    if not sfld:
        sfld = ""
    sfld = str(sfld)
    return sfld


def __on_browser_did_search(context: SearchContext) -> None:
    if not context.ids:
        return
    if isinstance(context.order, Column) and context.order.notes_mode_label == __COLUMN_LABEL:
        context.ids = sorted(context.ids, key=lambda item_id: __unicode_sort(item_id))


if enabled():
    gui_hooks.browser_did_fetch_columns.append(__add_browser_column)
    gui_hooks.browser_will_search.append(__on_search)
    gui_hooks.browser_did_fetch_row.append(__on_browser_did_fetch_row)
    gui_hooks.browser_did_search.append(__on_browser_did_search)

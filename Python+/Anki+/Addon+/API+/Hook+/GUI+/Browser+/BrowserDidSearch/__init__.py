# Sort notes
from typing import Sequence

from aqt import gui_hooks
from aqt.browser import SearchContext, Column, ItemId

from ._common.disable import enabled


def __modify_row(context: SearchContext) -> None:
    order: Column = context.order
    # sys.stderr.write(f"Context order: key={order.key}, sorting={order.sorting}")
    if context.ids:
        context.ids = __sort(context.ids)


def __sort(item_ids: Sequence[ItemId]) -> Sequence[ItemId]:
    return sorted(item_ids, reverse=True)


if enabled():
    gui_hooks.browser_did_search.append(__modify_row)

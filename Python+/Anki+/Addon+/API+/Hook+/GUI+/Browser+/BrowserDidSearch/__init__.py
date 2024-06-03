# Sort notes
from typing import Sequence

from aqt import gui_hooks
from aqt.browser import SearchContext, Column, ItemId


def modify_row(context: SearchContext) -> None:
    order: Column = context.order
    # sys.stderr.write(f"Context order: key={order.key}, sorting={order.sorting}")
    if context.ids:
        context.ids = sort(context.ids)


def sort(item_ids: Sequence[ItemId]) -> Sequence[ItemId]:
    return sorted(item_ids, reverse=True)


# gui_hooks.browser_did_search.append(modify_row)

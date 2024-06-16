from aqt import gui_hooks
from aqt.browser import SearchContext
from aqt.utils import showInfo

from ._common.disable import enabled


def _show_message(context: SearchContext):
    showInfo(f'Browser will search: order={context.order}, reverse={context.reverse}')


if enabled():
    gui_hooks.browser_will_search.append(_show_message)

from aqt import gui_hooks
from aqt.browser import SearchContext
from aqt.utils import show_info

from ._common.disable import enabled


def __show_message(context: SearchContext):
    show_info(f'Browser will search: order={context.order}, reverse={context.reverse}')


if enabled():
    gui_hooks.browser_will_search.append(__show_message)

from aqt import gui_hooks
from aqt.browser import SearchContext
from aqt.utils import showInfo


def show_message(context: SearchContext):
    showInfo(f'Browser will search: order={context.order}, reverse={context.reverse}')


# gui_hooks.browser_will_search.append(show_message)

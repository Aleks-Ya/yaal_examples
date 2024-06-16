from aqt.browser import Browser

from ._common.disable import enabled
from ._common import menu


def _search(browser: Browser):
    browser.search_for("tag:marked")
    browser.update_history()  # add current search string to history


if enabled():
    menu.add_browser_menu("Search marked", _search)

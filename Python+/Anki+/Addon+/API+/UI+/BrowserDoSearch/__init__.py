from aqt.browser import Browser

from ._common import menu


def search(browser: Browser):
    browser.search_for("tag:marked")
    browser.update_history()  # add current search string to history


menu.add_browser_menu("Search marked", search)

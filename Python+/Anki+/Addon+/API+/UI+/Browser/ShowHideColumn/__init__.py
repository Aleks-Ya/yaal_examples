from aqt.browser import Browser
from aqt.utils import show_info

from ._common.disable import enabled
from ._common import menu


def __on_show_due_column(browser: Browser):
    browser.form.tableView.showColumn("Due")


def __on_hide_due_column(browser: Browser):
    browser.form.tableView.itemDelegateForColumn()
    browser.form.tableView.hideColumn("Due")


def __on_list_columns(browser: Browser):
    count: int = browser.form.tableView.model().columnCount()
    # for i in range(0, count):
    #     column: int = browser.form.tableView.getumnAt(i)
    show_info(f"Column count: {count}")


if enabled():
    menu.add_browser_menu("Show column list", __on_list_columns)
    menu.add_browser_menu("Show column Due", __on_show_due_column)
    menu.add_browser_menu("Hide column Due", __on_hide_due_column)

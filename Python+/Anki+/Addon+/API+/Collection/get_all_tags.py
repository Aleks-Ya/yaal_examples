# Get all tags
from typing import List

from PyQt6.QtWidgets import QMenu
from aqt import mw
from aqt.qt import QAction, qconnect
from aqt.utils import showInfo


def _act() -> None:
    all_tags: List[str] = mw.col.tags.all()
    tag_number: int = len(all_tags)
    tags_10: List[str] = all_tags[:10]
    showInfo(f"Tag number={tag_number}, first 10 tags={tags_10}")


def add_menu_item(parent_menu: QMenu):
    action: QAction = QAction("Get all tags", mw)
    qconnect(action.triggered, _act)
    parent_menu.addAction(action)

import importlib.util
import os
import sys

from PyQt6.QtWidgets import QMenu
from aqt import mw
from aqt.qt import QAction, qconnect
from aqt.utils import showInfo

mem_top_installed = importlib.util.find_spec('mem_top') is not None
if mem_top_installed:
    raise RuntimeError("mem_top is installed already")

addon_dir = os.path.dirname(__file__)
sys.path.insert(1, os.path.abspath(os.path.dirname(__file__)))
sys.path.insert(1, os.path.join(addon_dir, 'mem_top'))
sys.path.insert(1, os.path.join(addon_dir, 'mem_top_client'))

import mem_top
import mem_top_client


def use_mem_top_from_init():
    showInfo("Mem Top (from init.py): %s" % mem_top.mem_top())


addon_name: str = os.path.basename(os.path.dirname(__file__))
parent_menu = QMenu(addon_name, mw)
mw.form.menuTools.addMenu(parent_menu)

action1 = QAction("Mem Top (directly)", mw)
qconnect(action1.triggered, use_mem_top_from_init)
parent_menu.addAction(action1)

action2 = QAction("Mem Top (through client)", mw)
qconnect(action2.triggered, mem_top_client.show_memory_usage)
parent_menu.addAction(action2)

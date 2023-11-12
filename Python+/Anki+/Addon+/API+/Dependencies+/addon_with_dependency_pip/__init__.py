# Imports and run mem_top module
# Prepare "bundled_dependencies": pip install --target=./Addon+/API+/Dependencies+/addon_with_dependency_pip/bundled_dependencies mem_top
# Run: "Tools" - Mem Top" menu
import importlib.util
import os
import sys

from PyQt6.QtWidgets import QMenu
from aqt import mw
from aqt.qt import QAction, qconnect
from aqt.utils import showInfo

mem_top_installed = importlib.util.find_spec('pyyaml') is not None
if mem_top_installed:
    raise RuntimeError("pyyaml is installed already")

addon_dir = os.path.dirname(__file__)
sys.path.insert(1, os.path.abspath(os.path.dirname(__file__)))
sys.path.insert(1, os.path.join(addon_dir, 'bundled_dependencies'))

import yaml
import client


def use_from_init():
    showInfo("pyyaml from __init__.py: %s" % yaml.safe_load("person: John"))


addon_name: str = os.path.basename(os.path.dirname(__file__))
parent_menu = QMenu(addon_name, mw)
mw.form.menuTools.addMenu(parent_menu)

action1 = QAction("Run from __init__.py", mw)
qconnect(action1.triggered, use_from_init)
parent_menu.addAction(action1)

action2 = QAction("Run from client", mw)
qconnect(action2.triggered, client.use_from_client)
parent_menu.addAction(action2)

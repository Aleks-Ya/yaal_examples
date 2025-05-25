# Imports and run mem_top module
# Prepare "bundled_dependencies": pip install --target=./Addon+/API+/Dependencies+/addon_with_dependency_pip/bundled_dependencies mem_top
# Run: "Tools" - Mem Top" menu
import importlib.util
import os
import sys

from aqt.utils import show_info

from ._common.disable import enabled
from ._common import menu

__mem_top_installed = importlib.util.find_spec('pyyaml') is not None
if __mem_top_installed:
    raise RuntimeError("pyyaml is installed already")

__addon_dir = os.path.dirname(__file__)
sys.path.insert(1, os.path.abspath(os.path.dirname(__file__)))
sys.path.insert(1, os.path.join(__addon_dir, 'bundled_dependencies'))

import yaml  # will be imported from dependencies
import client


def __use_from_init():
    show_info("pyyaml from __init__.py: %s" % yaml.safe_load("person: John"))


if enabled():
    menu.add_mw_menu_item("Run from __init__.py", __use_from_init)
    menu.add_mw_menu_item("Run from client", client.use_from_client)

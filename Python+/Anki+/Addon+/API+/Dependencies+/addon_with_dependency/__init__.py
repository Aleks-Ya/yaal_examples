import importlib.util
import os
import sys

from aqt.utils import showInfo

from ._common import menu

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


menu.add_mw_menu_item("Mem Top (directly)", use_mem_top_from_init)
menu.add_mw_menu_item("Mem Top (through client)", mem_top_client.show_memory_usage)

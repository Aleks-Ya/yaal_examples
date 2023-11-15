import logging
import os
import sys
from pathlib import Path

from PyQt6.QtWidgets import QMenu
from aqt import mw

init_py_file: Path = Path(__file__)
addon_dir: Path = init_py_file.parent
addon_name: str = addon_dir.name
log_file = os.path.join(addon_dir, f"{addon_name}.log")
log: logging.Logger = logging.getLogger(addon_name)
log.setLevel(logging.DEBUG)
handler: logging.FileHandler = logging.FileHandler(log_file)
handler.setLevel(logging.DEBUG)
handler.setFormatter(logging.Formatter('%(asctime)s %(name)s %(funcName)s %(levelname)s %(message)s'))
log.addHandler(handler)
log.info(f'Logger is configured: file={log_file}')

addon_dir = os.path.dirname(__file__)
sys.path.insert(1, os.path.abspath(os.path.dirname(__file__)))
sys.path.insert(1, os.path.join(addon_dir, 'bundled_dependencies'))

log.info(f"sys.path={sys.path}")

from . import synonyms_antonyms
from . import description

parent_menu: QMenu = QMenu("OpenAI", mw)
mw.form.menuTools.addMenu(parent_menu)
parent_menu.addAction(synonyms_antonyms.menu_action())
parent_menu.addAction(description.menu_action())

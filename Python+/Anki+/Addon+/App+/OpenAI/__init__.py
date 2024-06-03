import logging
import os
import sys
from pathlib import Path

from PyQt6.QtWidgets import QMenu
from aqt import mw, gui_hooks
from aqt.browser import Browser

from .common.config import LanguageAiConfig

init_py_file: Path = Path(__file__)
addon_dir: Path = init_py_file.parent
addon_name: str = addon_dir.name
log_file: str = os.path.join(addon_dir, f"{addon_name}.log")
root: logging.Logger = logging.getLogger()
handler: logging.FileHandler = logging.FileHandler(log_file)
handler.setLevel(logging.DEBUG)
handler.setFormatter(logging.Formatter('%(asctime)s %(name)s %(funcName)s %(levelname)s %(message)s'))
root.addHandler(handler)
log: logging.Logger = logging.getLogger(__name__)
log.setLevel(logging.DEBUG)
log.info(f'Logger is configured: file={log_file}')

addon_dir = os.path.dirname(__file__)
sys.path.insert(1, os.path.abspath(addon_dir))
sys.path.insert(1, os.path.join(addon_dir, 'bundled_dependencies'))

log.info(f"sys.path={sys.path}")

from synonyms_antonyms.synonyms_antonyms import SynonymsAntonyms
from chinese.chinese import Chinese
from definition.definition import Definition

config: LanguageAiConfig = LanguageAiConfig(mw.addonManager.getConfig(__name__))
log.info(f"Config: {config}")
synonyms_antonyms: SynonymsAntonyms = SynonymsAntonyms(config)
definition: Definition = Definition(config)
chinese: Chinese = Chinese(config)

parent_menu: QMenu = QMenu("LanguageAI", mw)
mw.form.menuTools.addMenu(parent_menu)
parent_menu.addAction(synonyms_antonyms.process_all_menu_action(mw))
parent_menu.addAction(definition.menu_action())
parent_menu.addAction(chinese.menu_action())


def add_browser_menu(browser: Browser) -> None:
    parent: QMenu = QMenu("LanguageAI", browser)
    browser.form.menubar.addMenu(parent)
    parent.addAction(synonyms_antonyms.process_all_menu_action(browser))
    parent.addAction(synonyms_antonyms.process_selected_menu_action(browser))


gui_hooks.browser_will_show.append(add_browser_menu)

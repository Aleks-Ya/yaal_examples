from typing import Callable
from logging import getLogger, Logger, DEBUG, FileHandler, Formatter
import os
from pathlib import Path

import aqt


class ProfileDidOpenHook(Callable[[], None]):
    def __init__(self, dry_run: bool = True):
        self.__initialized: bool = False
        self.__dry_run: bool = dry_run

    def __call__(self) -> None:
        from aqt import mw, gui_hooks
        from .clean_all_notes_action import CleanAllNotesAction
        if self.__initialized:
            return
        self.__initialized = True
        init_py_file: Path = Path(__file__)
        addon_dir: Path = init_py_file.parent
        addon_name: str = addon_dir.name
        log_file: str = os.path.join(addon_dir, f"{addon_name}.log")
        log: Logger = getLogger(addon_name)
        log.setLevel(DEBUG)
        handler: FileHandler = FileHandler(log_file)
        handler.setLevel(DEBUG)
        handler.setFormatter(Formatter('%(asctime)s %(name)s %(funcName)s %(levelname)s %(message)s'))
        log.addHandler(handler)
        log.info(f'Logger is configured: file={log_file}')

        clean_all_notes_action: CleanAllNotesAction = CleanAllNotesAction(mw, self.__dry_run)
        mw.form.menuTools.addAction(clean_all_notes_action)

        gui_hooks.browser_will_show_context_menu.append(self.__on_browser_will_show_context_menu)

    def __on_browser_will_show_context_menu(self, browser: 'aqt.browser.Browser', menu: 'aqt.QMenu') -> None:
        from .clean_selected_notes_action import CleanSelectedNotesAction
        action: CleanSelectedNotesAction = CleanSelectedNotesAction(browser, self.__dry_run)
        menu.addAction(action)

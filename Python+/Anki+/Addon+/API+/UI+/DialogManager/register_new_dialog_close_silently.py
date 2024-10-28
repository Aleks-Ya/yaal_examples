from typing import Any

from PyQt6.QtWidgets import QPushButton
from aqt import mw, dialogs, QDialog

from ._common.disable import enabled
from ._common import menu


class MoodDialog(QDialog):
    name: str = "MoodDialogSilent"
    silentlyClose = True

    def __init__(self):
        super().__init__(mw)
        button: QPushButton = QPushButton("I'm good", self)
        # noinspection PyUnresolvedReferences
        button.clicked.connect(self.close)

    def reopen(self, *args: Any, **kwargs: Any):
        # noinspection PyUnresolvedReferences
        self.show()

    def reject(self) -> None:
        dialogs.markClosed(self.name)
        super().reject()


def __on_register_dialog():
    mood_dialog: MoodDialog = MoodDialog()
    dialogs.register_dialog(MoodDialog.name, None, mood_dialog)


def __on_open_mood_dialog():
    dialogs.open(MoodDialog.name, mw.addonManager)


def init():
    if enabled(False):
        menu.add_mw_menu_item(f"Register in DialogManager: {MoodDialog.name}", __on_register_dialog)
        menu.add_mw_menu_item(f"Open by DialogManager: {MoodDialog.name}", __on_open_mood_dialog)

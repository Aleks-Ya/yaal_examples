from aqt import mw
from aqt.qt import QDialog, QVBoxLayout, QDialogButtonBox
from aqt.utils import showInfo

from ._common.disable import enabled
from ._common import menu


def __accept():
    showInfo("Accept")


def __reject():
    showInfo("Reject")


def __show_dialog() -> None:
    dialog: QDialog = QDialog(mw)
    layout: QVBoxLayout = QVBoxLayout(dialog)

    button_box: QDialogButtonBox = QDialogButtonBox(
        QDialogButtonBox.StandardButton.Ok | QDialogButtonBox.StandardButton.Cancel)
    button_box.accepted.connect(__accept)
    button_box.rejected.connect(__reject)
    layout.addWidget(button_box)
    dialog.setLayout(layout)
    dialog.exec()


if enabled():
    menu.add_mw_menu_item("Show dialog", __show_dialog)

from typing import Optional

from aqt import mw
from aqt.qt import QDialog, QVBoxLayout, QDialogButtonBox, QWidget, QTabWidget, QCheckBox, QComboBox, QHBoxLayout, \
    QLabel, QGroupBox, QTableWidget, QTableWidgetItem, QFrame, QPushButton, QColor, QColorDialog, Qt
from aqt.utils import show_info

from ._common.disable import enabled
from ._common import menu


class SizeButtonEnabledLayout(QVBoxLayout):
    def __init__(self, *args, **kwargs):
        super().__init__(*args, **kwargs)
        group_box: QGroupBox = QGroupBox('Show "Size Button":')
        group_checkbox: QCheckBox = QCheckBox('Enable "Size Button"')
        group_checkbox.stateChanged.connect(lambda state: group_box.setEnabled(state == 2))
        group_checkbox.setCheckState(Qt.CheckState.Checked)
        checkbox1: QCheckBox = QCheckBox("During adding new note")
        checkbox2: QCheckBox = QCheckBox("During editing note in Browser")
        checkbox3: QCheckBox = QCheckBox("During editing current note while studying")
        group_layout: QVBoxLayout = QVBoxLayout()
        group_layout.addWidget(checkbox1)
        group_layout.addWidget(checkbox2)
        group_layout.addWidget(checkbox3)
        group_box.setLayout(group_layout)
        self.addWidget(group_checkbox)
        self.addWidget(group_box)


class ColorsLayout(QVBoxLayout):
    def __init__(self, *args, **kwargs):
        super().__init__(*args, **kwargs)
        group_box: QGroupBox = QGroupBox('Color levels:')
        group_checkbox: QCheckBox = QCheckBox('Enable colors')
        group_checkbox.stateChanged.connect(lambda state: group_box.setEnabled(state == 2))
        group_checkbox.setCheckState(Qt.CheckState.Checked)
        rows: int = 3
        columns: int = 3
        self.table: QTableWidget = QTableWidget(rows, columns)
        self.table.setHorizontalHeaderLabels(["Color", "Min Size", "Max Size"])
        self.table.setItem(0, 0, QTableWidgetItem(""))
        self.table.setItem(0, 1, QTableWidgetItem("0 B"))
        self.table.setItem(0, 2, QTableWidgetItem("100 KB"))
        green: QColor = QColor("green")
        self.table.item(0, 0).setBackground(green)

        self.table.cellClicked.connect(self.__open_color_dialog)

        add_button: QPushButton = QPushButton("Add Row")
        add_button.clicked.connect(self.__add_row)

        remove_button: QPushButton = QPushButton("Remove Row")
        remove_button.clicked.connect(self.__remove_row)

        group_layout: QVBoxLayout = QVBoxLayout()
        group_layout.addWidget(self.table)
        group_layout.addWidget(add_button)
        group_layout.addWidget(remove_button)
        group_box.setLayout(group_layout)
        self.addWidget(group_checkbox)
        self.addWidget(group_box)
        self.addWidget(add_button)
        self.addWidget(remove_button)

    def __add_row(self) -> None:
        self.table.insertRow(self.table.rowCount())

    def __remove_row(self) -> None:
        current_row: int = self.table.currentRow()
        if current_row != -1:
            self.table.removeRow(current_row)

    def __open_color_dialog(self, row, column):
        if column == 0:
            color: Optional[QColor] = QColorDialog.getColor()
            if color.isValid():
                self.table.item(row, column).setBackground(color)


def __accept():
    show_info("Accept")


def __reject():
    show_info("Reject")


def __show_dialog() -> None:
    dialog: QDialog = QDialog(mw)
    dialog.setWindowTitle('"Note Size" addon configuration')

    layout: QVBoxLayout = QVBoxLayout(dialog)

    tab_widget: QTabWidget = QTabWidget(dialog)

    tab_widget.addTab(__collection_size_tab(), "Collection size")
    tab_widget.addTab(__size_button_tab(), "Size button")
    tab_widget.addTab(__logging_tab(), "Logging")
    tab_widget.addTab(__cache_tab(), "Cache")
    tab_widget.adjustSize()

    button_box: QDialogButtonBox = QDialogButtonBox(
        QDialogButtonBox.StandardButton.Ok | QDialogButtonBox.StandardButton.Cancel)
    button_box.accepted.connect(__accept)
    button_box.rejected.connect(__reject)

    layout.addWidget(tab_widget)
    layout.addWidget(button_box)

    dialog.setLayout(layout)
    dialog.adjustSize()
    dialog.show()


def __collection_size_tab() -> QWidget:
    tab: QWidget = QWidget()
    layout: QVBoxLayout = QVBoxLayout()
    layout.setAlignment(Qt.AlignmentFlag.AlignTop)
    checkbox: QCheckBox = QCheckBox("Show collection size in Deck Browser")
    layout.addWidget(checkbox)
    tab.setLayout(layout)
    return tab


def __size_button_tab() -> QWidget:
    tab: QWidget = QWidget()

    button_enabled_layout: SizeButtonEnabledLayout = SizeButtonEnabledLayout()
    colors_layout: ColorsLayout = ColorsLayout()

    horizontal_rule: QFrame = QFrame()
    horizontal_rule.setFrameShape(QFrame.Shape.HLine)
    horizontal_rule.setFrameShadow(QFrame.Shadow.Sunken)

    layout: QVBoxLayout = QVBoxLayout()
    layout.addLayout(button_enabled_layout)
    layout.addWidget(horizontal_rule)
    layout.addLayout(colors_layout)
    tab.setLayout(layout)
    return tab


def __logging_tab() -> QWidget:
    tab: QWidget = QWidget()
    layout: QVBoxLayout = QVBoxLayout()
    layout.setAlignment(Qt.AlignmentFlag.AlignTop)
    combo_box_layout: QHBoxLayout = QHBoxLayout()
    combo_box_layout.setAlignment(Qt.AlignmentFlag.AlignLeft)
    label: QLabel = QLabel('Log level:')
    combo_box: QComboBox = QComboBox()
    combo_box.addItems(["NOTSET", "DEBUG", "INFO", "WARNING", "ERROR", "CRITICAL"])
    combo_box_layout.addWidget(label)
    combo_box_layout.addWidget(combo_box)
    layout.addLayout(combo_box_layout)
    tab.setLayout(layout)
    return tab


def __cache_tab() -> QWidget:
    tab: QWidget = QWidget()
    layout: QVBoxLayout = QVBoxLayout()
    layout.setAlignment(Qt.AlignmentFlag.AlignTop)
    checkbox: QCheckBox = QCheckBox("Enable cache warm-up")
    layout.addWidget(checkbox)
    tab.setLayout(layout)
    return tab


if enabled():
    menu.add_mw_menu_item("Show config dialog", __show_dialog)

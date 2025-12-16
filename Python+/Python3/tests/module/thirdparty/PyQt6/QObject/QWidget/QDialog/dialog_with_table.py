from PyQt6.QtCore import QSize, QRect
from PyQt6.QtWidgets import QDialog, QVBoxLayout, QLabel, QPushButton, QApplication, QTableWidget, QWIDGETSIZE_MAX, \
    QSizePolicy, QAbstractScrollArea, QHeaderView

from tests.module.thirdparty.PyQt6 import app, assert_widget_size, assert_scroll_area_size
from tests.module.thirdparty.PyQt6.QObject.QWidget.QTableWidget.data import create_table_with_headers

with app():
    table: QTableWidget = create_table_with_headers()
    assert_widget_size(
        widget=table,
        size=QSize(640, 480),
        minimum_size=QSize(0, 0),
        maximum_size=QSize(QWIDGETSIZE_MAX, QWIDGETSIZE_MAX),
        size_hint=QSize(256, 192),
        minimum_size_hint=QSize(70, 70),
        size_policy=QSizePolicy(QSizePolicy.Policy.Expanding, QSizePolicy.Policy.Expanding),
        size_increment=QSize(0, 0),
        base_size=QSize(0, 0),
        frame_size=QSize(640, 480),
        geometry=QRect(0, 0, 640, 480)
    )
    assert_scroll_area_size(scroll_area=table,
                            size_adjust_policy=QAbstractScrollArea.SizeAdjustPolicy.AdjustIgnored)

    table.setSizeAdjustPolicy(QTableWidget.SizeAdjustPolicy.AdjustToContents)
    assert_widget_size(
        widget=table,
        size=QSize(640, 480),
        minimum_size=QSize(0, 0),
        maximum_size=QSize(QWIDGETSIZE_MAX, QWIDGETSIZE_MAX),
        size_hint=QSize(315, 113),
        minimum_size_hint=QSize(70, 70),
        size_policy=QSizePolicy(QSizePolicy.Policy.Expanding, QSizePolicy.Policy.Expanding),
        size_increment=QSize(0, 0),
        base_size=QSize(0, 0),
        frame_size=QSize(640, 480),
        geometry=QRect(0, 0, 640, 480)
    )
    assert_scroll_area_size(scroll_area=table,
                            size_adjust_policy=QAbstractScrollArea.SizeAdjustPolicy.AdjustToContents)

    table.horizontalHeader().setSectionResizeMode(QHeaderView.ResizeMode.ResizeToContents)
    table.verticalHeader().setSectionResizeMode(QHeaderView.ResizeMode.ResizeToContents)
    table.horizontalHeader().setSectionResizeMode(0, QHeaderView.ResizeMode.Stretch)
    assert_widget_size(
        widget=table,
        size=QSize(640, 480),
        minimum_size=QSize(0, 0),
        maximum_size=QSize(QWIDGETSIZE_MAX, QWIDGETSIZE_MAX),
        size_hint=QSize(964, 117),
        minimum_size_hint=QSize(70, 70),
        size_policy=QSizePolicy(QSizePolicy.Policy.Expanding, QSizePolicy.Policy.Expanding),
        size_increment=QSize(0, 0),
        base_size=QSize(0, 0),
        frame_size=QSize(640, 480),
        geometry=QRect(0, 0, 640, 480)
    )

    dialog: QDialog = QDialog()
    dialog.setWindowTitle("Dialog with a table")

    label: QLabel = QLabel("Participant list:")

    button: QPushButton = QPushButton("Close")
    button.clicked.connect(dialog.close)

    layout: QVBoxLayout = QVBoxLayout()
    layout.addWidget(label)
    layout.addWidget(table)
    layout.addWidget(button)

    dialog.setLayout(layout)
    dialog.finished.connect(QApplication.quit)

    dialog.show()

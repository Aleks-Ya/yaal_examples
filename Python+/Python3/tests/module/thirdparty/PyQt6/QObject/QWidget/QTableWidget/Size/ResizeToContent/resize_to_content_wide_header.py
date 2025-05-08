# Resize to content for a wide header
from PyQt6.QtCore import QSize, QRect
from PyQt6.QtWidgets import QTableWidget, QHeaderView, QWIDGETSIZE_MAX, QSizePolicy, QTableWidgetItem

from src.module.thirdparty.PyQt6 import app, assert_widget_size

with app():
    table: QTableWidget = QTableWidget(3, 3)
    table.setHorizontalHeaderLabels(["Name " * 6, "Age " * 4, "City " * 5])

    table.setItem(0, 0, QTableWidgetItem("John"))
    table.setItem(0, 1, QTableWidgetItem("40"))
    table.setItem(0, 2, QTableWidgetItem("London"))

    table.setItem(1, 0, QTableWidgetItem("Mary"))
    table.setItem(1, 1, QTableWidgetItem("30"))
    table.setItem(1, 2, QTableWidgetItem("Berlin"))

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

    table.resizeColumnsToContents()
    table.resizeRowsToContents()
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

    table.horizontalHeader().setSectionResizeMode(0, QHeaderView.ResizeMode.Stretch)
    table.setSizeAdjustPolicy(QTableWidget.SizeAdjustPolicy.AdjustToContents)
    table.show()

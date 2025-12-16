from PyQt6.QtWidgets import QWidget, QTabWidget, QVBoxLayout, QLabel

from tests.module.thirdparty.PyQt6 import app

with app():
    tab1_layout: QVBoxLayout = QVBoxLayout()
    tab1_layout.addWidget(QLabel("Content of Tab 1"))
    tab1: QWidget = QWidget()
    tab1.setLayout(tab1_layout)

    tab2_layout: QVBoxLayout = QVBoxLayout()
    tab2_layout.addWidget(QLabel("Content of Tab 2"))
    tab2: QWidget = QWidget()
    tab2.setLayout(tab2_layout)

    tab_widget: QTabWidget = QTabWidget()
    tab_widget.addTab(tab1, "Tab 1")
    tab_widget.addTab(tab2, "Tab 2")
    tab_widget.show()

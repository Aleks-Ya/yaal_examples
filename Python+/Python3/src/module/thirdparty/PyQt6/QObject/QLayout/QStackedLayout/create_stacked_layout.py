from PyQt6.QtWidgets import QPushButton, QStackedLayout, QComboBox, QVBoxLayout

from src.module.thirdparty.PyQt6 import window

with window() as window:
    button1: QPushButton = QPushButton('Button 1')
    button2: QPushButton = QPushButton('Button 2')

    stacked_layout: QStackedLayout = QStackedLayout()
    stacked_layout.addWidget(button1)
    stacked_layout.addWidget(button2)


    def switch_page(index: int):
        stacked_layout.setCurrentIndex(index)


    page_combo: QComboBox = QComboBox()
    page_combo.addItems(["Page 1", "Page 2"])
    page_combo.activated.connect(switch_page)

    main_layout: QVBoxLayout = QVBoxLayout()
    main_layout.addWidget(page_combo)
    main_layout.addLayout(stacked_layout)

    window.setLayout(main_layout)

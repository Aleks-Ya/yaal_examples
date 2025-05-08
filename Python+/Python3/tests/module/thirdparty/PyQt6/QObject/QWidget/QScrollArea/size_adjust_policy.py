from PyQt6.QtWidgets import QLabel, QScrollArea, QAbstractScrollArea

from src.module.thirdparty.PyQt6 import app, assert_scroll_area_size

with app():
    label: QLabel = QLabel('abc ' * 40)
    scroll_area: QScrollArea = QScrollArea()
    scroll_area.setWidget(label)
    scroll_area.setSizeAdjustPolicy(QScrollArea.SizeAdjustPolicy.AdjustToContents)
    scroll_area.setWidgetResizable(True)
    scroll_area.adjustSize()
    scroll_area.show()
    assert_scroll_area_size(scroll_area=scroll_area,
                            size_adjust_policy=QAbstractScrollArea.SizeAdjustPolicy.AdjustToContents)

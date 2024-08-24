from PyQt6.QtWidgets import QStyleFactory, QStyle

style: QStyle = QStyleFactory.create('Fusion')
print(style.objectName())

from PyQt6.QtCore import QRect
from PyQt6.QtWidgets import QApplication
from PyQt6.QtGui import QScreen

app: QApplication = QApplication([])

screen: QScreen = app.primaryScreen()
available_geometry: QRect = screen.availableGeometry()
width: int = available_geometry.width()
height: int = available_geometry.height()

print(f"Width: {width}, Height: {height}")

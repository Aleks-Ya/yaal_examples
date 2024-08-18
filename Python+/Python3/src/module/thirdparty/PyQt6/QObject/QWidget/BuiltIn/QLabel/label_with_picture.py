from PyQt6.QtCore import Qt
from PyQt6.QtGui import QPixmap
from PyQt6.QtWidgets import QApplication, QLabel, QWidget

app: QApplication = QApplication([])
window: QWidget = QWidget()
window.setGeometry(200, 100, 400, 300)

pixmap: QPixmap = QPixmap("info.png").scaled(30, 30, Qt.AspectRatioMode.KeepAspectRatio,
                                             Qt.TransformationMode.SmoothTransformation)
label: QLabel = QLabel(window)
label.setText('TEXT IS NOT DISPLAYED')
label.setPixmap(pixmap)

window.show()

app.exec()

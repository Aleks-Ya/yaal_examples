import sys

from PyQt5 import QtCore, QtWidgets
from PyQt5.QtCore import QSize, QRect
from PyQt5.QtWidgets import QMainWindow, QLabel, QGridLayout, QWidget, QComboBox, QCheckBox, QPushButton


class BrowserDialogWindow(QMainWindow):
    def __init__(self):
        QMainWindow.__init__(self)

        self.setMinimumSize(QSize(640, 200))
        self.setWindowTitle("Transcription")

        central_widget = QWidget(self)
        self.setCentralWidget(central_widget)

        grid_layout = QGridLayout(self)
        central_widget.setLayout(grid_layout)

        # text field
        text_field_label = QLabel("Text field:", self)
        text_field_label.setAlignment(QtCore.Qt.AlignRight)

        text_field_combo_box = QComboBox(central_widget)
        text_field_combo_box.setGeometry(QRect(40, 40, 491, 31))
        text_field_combo_box.setObjectName("text_field")
        text_field_combo_box.addItem("PyQt")
        text_field_combo_box.addItem("Qt")
        text_field_combo_box.addItem("Python")
        text_field_combo_box.addItem("Example")

        # transcription field
        transcription_field_label = QLabel("Transcription field:", self)
        transcription_field_label.setAlignment(QtCore.Qt.AlignRight)

        transcription_field_combo_box = QComboBox(central_widget)
        transcription_field_combo_box.setGeometry(QRect(40, 40, 491, 31))
        transcription_field_combo_box.setObjectName("transcription_field")
        transcription_field_combo_box.addItem("PyQt")
        transcription_field_combo_box.addItem("Qt")
        transcription_field_combo_box.addItem("Python")
        transcription_field_combo_box.addItem("Example")

        # language field
        language_field_label = QLabel("Language:", self)
        language_field_label.setAlignment(QtCore.Qt.AlignRight)

        language_field_combo_box = QComboBox(central_widget)
        language_field_combo_box.setGeometry(QRect(40, 40, 491, 31))
        language_field_combo_box.setObjectName("language_field")
        language_field_combo_box.addItem("English")
        language_field_combo_box.addItem("Russian")

        # overwrite exist
        overwrite_exists_check_box = QCheckBox("Overwrite exist", self)
        # self.b.stateChanged.connect(self.click_box)
        overwrite_exists_check_box.move(20, 20)
        overwrite_exists_check_box.resize(320, 40)

        # start button
        start_button = QPushButton('Start', self)
        start_button.resize(100, 32)
        start_button.move(50, 50)

        # cancel button
        cancel_button = QPushButton('Cancel', self)
        cancel_button.resize(100, 32)
        cancel_button.move(50, 50)

        first_column = 0
        second_column = 1

        text_field_row = 0
        grid_layout.addWidget(text_field_label, text_field_row, first_column)
        grid_layout.addWidget(text_field_combo_box, text_field_row, second_column)

        transcription_field_row = 1
        grid_layout.addWidget(transcription_field_label, transcription_field_row, first_column)
        grid_layout.addWidget(transcription_field_combo_box, transcription_field_row, second_column)

        language_field_row = 2
        grid_layout.addWidget(language_field_label, language_field_row, first_column)
        grid_layout.addWidget(language_field_combo_box, language_field_row, second_column)

        overwrite_exists_row = 3
        grid_layout.addWidget(overwrite_exists_check_box, overwrite_exists_row, second_column)

        buttons_exists_row = 4
        grid_layout.addWidget(start_button, buttons_exists_row, first_column)
        grid_layout.addWidget(cancel_button, buttons_exists_row, second_column)


if __name__ == "__main__":
    app = QtWidgets.QApplication(sys.argv)
    mainWin = BrowserDialogWindow()
    mainWin.show()
    sys.exit(app.exec_())

import sys

from PyQt6 import QtCore, QtWidgets
from PyQt6.QtCore import QSize, QRect
from PyQt6.QtWidgets import QMainWindow, QLabel, QGridLayout, QWidget, QComboBox, QCheckBox, QPushButton

from apps.transcription_service.ui.controller import Controller


class BrowserDialogWindow(QMainWindow):
    def __init__(self, ui_controller):
        self._controller = ui_controller
        self.overrideExists = False

        QMainWindow.__init__(self)

        self.setMinimumSize(QSize(640, 200))
        self.setWindowTitle("Transcription")

        central_widget = QWidget(self)
        self.setCentralWidget(central_widget)

        grid_layout = QGridLayout(self)
        central_widget.setLayout(grid_layout)

        fields = self._controller.get_fields(self)

        # text field
        text_field_label = QLabel("Text field:", self)
        text_field_label.setAlignment(QtCore.Qt.AlignmentFlag.AlignRight)

        self.text_field_combo_box = QComboBox(central_widget)
        self.text_field_combo_box.setGeometry(QRect(40, 40, 491, 31))
        self.text_field_combo_box.setObjectName("text_field")
        self.text_field_combo_box.addItems(fields)

        # transcription field
        transcription_field_label = QLabel("Transcription field:", self)
        transcription_field_label.setAlignment(QtCore.Qt.AlignmentFlag.AlignRight)

        self.transcription_field_combo_box = QComboBox(central_widget)
        self.transcription_field_combo_box.setGeometry(QRect(40, 40, 491, 31))
        self.transcription_field_combo_box.setObjectName("transcription_field")
        self.transcription_field_combo_box.addItems(fields)

        # language field
        language_field_label = QLabel("Language:", self)
        language_field_label.setAlignment(QtCore.Qt.AlignmentFlag.AlignRight)

        languages = self._controller.get_languages(self)
        self.language_field_combo_box = QComboBox(central_widget)
        self.language_field_combo_box.setGeometry(QRect(40, 40, 491, 31))
        self.language_field_combo_box.setObjectName("language_field")
        self.language_field_combo_box.addItems(languages)

        # overwrite exist
        self.overwrite_exists_check_box = QCheckBox("Overwrite exist", self)
        self.overwrite_exists_check_box.move(20, 20)
        self.overwrite_exists_check_box.resize(320, 40)

        # start button
        start_button = QPushButton('Start', self)
        start_button.resize(100, 32)
        start_button.move(50, 50)
        start_button.clicked.connect(self.start)

        # cancel button
        cancel_button = QPushButton('Cancel', self)
        cancel_button.resize(100, 32)
        cancel_button.move(50, 50)
        cancel_button.clicked.connect(self.close)

        first_column = 0
        second_column = 1

        text_field_row = 0
        grid_layout.addWidget(text_field_label, text_field_row, first_column)
        grid_layout.addWidget(self.text_field_combo_box, text_field_row, second_column)

        transcription_field_row = 1
        grid_layout.addWidget(transcription_field_label, transcription_field_row, first_column)
        grid_layout.addWidget(self.transcription_field_combo_box, transcription_field_row, second_column)

        language_field_row = 2
        grid_layout.addWidget(language_field_label, language_field_row, first_column)
        grid_layout.addWidget(self.language_field_combo_box, language_field_row, second_column)

        overwrite_exists_row = 3
        grid_layout.addWidget(self.overwrite_exists_check_box, overwrite_exists_row, second_column)

        buttons_exists_row = 4
        grid_layout.addWidget(start_button, buttons_exists_row, first_column)
        grid_layout.addWidget(cancel_button, buttons_exists_row, second_column)

    def start(self):
        self._controller.start(self._controller, self.text_field_combo_box.currentText(),
                               self.transcription_field_combo_box.currentText(),
                               self.language_field_combo_box.currentText(),
                               self.overwrite_exists_check_box.isChecked())


if __name__ == "__main__":
    app = QtWidgets.QApplication(sys.argv)
    controller = Controller
    mainWin = BrowserDialogWindow(controller)
    mainWin.show()
    sys.exit(app.exec())

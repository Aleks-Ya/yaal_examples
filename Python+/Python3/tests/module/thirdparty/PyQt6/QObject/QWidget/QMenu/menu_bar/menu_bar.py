from PyQt6 import QtCore
from PyQt6.QtGui import QAction, QKeySequence
from PyQt6.QtWidgets import QLabel, QMenu, QMessageBox, QMenuBar

from src.module.thirdparty.PyQt6 import main_window

with main_window() as main_window:
    def __show_message():
        QMessageBox.information(main_window, 'Message', 'This is a message box')


    message_action: QAction = QAction('Show message', main_window)
    message_shortcut: str = 'Ctrl+M'
    message_action.setShortcut(QKeySequence(message_shortcut))
    message_action.triggered.connect(__show_message)

    exit_action: QAction = QAction('Exit', main_window)
    quit_shortcut: str = 'Ctrl+Q'
    exit_action.setShortcut(QKeySequence(quit_shortcut))
    exit_action.triggered.connect(main_window.close)

    file_menu: QMenu = QMenu('File', main_window)
    file_menu.addAction(message_action)
    file_menu.addAction(exit_action)

    label: QLabel = QLabel(f'Use "File" menu item, {message_shortcut}, or {quit_shortcut}')
    label.setAlignment(QtCore.Qt.AlignmentFlag.AlignCenter)
    main_window.setCentralWidget(label)

    menubar: QMenuBar = main_window.menuBar()
    menubar.addMenu(file_menu)

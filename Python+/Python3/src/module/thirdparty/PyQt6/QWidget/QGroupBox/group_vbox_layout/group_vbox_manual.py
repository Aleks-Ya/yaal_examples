from PyQt6.QtWidgets import QApplication, QCheckBox, QPushButton, QHBoxLayout

from group_vbox import GroupVBox

app: QApplication = QApplication([])

checkbox1: QCheckBox = QCheckBox("Option 1")
checkbox2: QCheckBox = QCheckBox("Option 2")

button1: QPushButton = QPushButton("Button 1")
button2: QPushButton = QPushButton("Button 2")
button_layout: QHBoxLayout = QHBoxLayout()
button_layout.addWidget(button1)
button_layout.addWidget(button2)

group_box: GroupVBox = GroupVBox('Group VBox options')
group_box.add_widget(checkbox1)
group_box.add_widget(checkbox2)
group_box.add_layout(button_layout)

group_box.show()

app.exec()

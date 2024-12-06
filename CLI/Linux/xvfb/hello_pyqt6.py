import sys
import os
import subprocess
from PyQt6.QtWidgets import QApplication, QLabel

print(f"""Display is '{os.environ.get("DISPLAY")}'""")

result = subprocess.run(['xrandr', '--current', '--listmonitors'], capture_output=True, text=True)
print(result.stdout)

app: QApplication = QApplication(sys.argv)
label: QLabel = QLabel("Hello, World!")
label.show()
sys.exit(app.exec())

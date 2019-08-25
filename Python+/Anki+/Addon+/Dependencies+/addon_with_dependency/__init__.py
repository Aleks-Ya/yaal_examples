# Imports and run mem_top module 
# Run: "Tools" - Mem Top" menu

from .mem_top import mem_top
from aqt import mw
from aqt.utils import showInfo
from aqt.qt import *

def testFunction():
    showInfo("Mem Top: %s" % mem_top.mem_top())

action = QAction("Mem Top", mw)
action.triggered.connect(testFunction)
mw.form.menuTools.addAction(action)

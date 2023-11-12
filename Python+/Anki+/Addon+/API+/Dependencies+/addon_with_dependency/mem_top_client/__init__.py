import mem_top
from aqt.utils import showInfo


def show_memory_usage():
    showInfo("Mem Top: %s" % mem_top.mem_top())

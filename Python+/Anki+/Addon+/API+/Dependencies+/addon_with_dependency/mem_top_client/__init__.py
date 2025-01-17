import mem_top
from aqt.utils import show_info


def show_memory_usage():
    show_info("Mem Top: %s" % mem_top.mem_top())

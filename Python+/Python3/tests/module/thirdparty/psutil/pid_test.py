import random

import psutil
from psutil import Process


def test_get_process_name_by_pid():
    pids: list[int] = psutil.pids()
    pid: int = random.choice(pids)
    p: Process = Process(pid)
    process_name: str = p.name()
    print(f"Name: {process_name}")

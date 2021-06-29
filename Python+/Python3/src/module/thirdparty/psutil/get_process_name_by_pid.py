import random

from psutil import Process, pids

pids = pids()
pid = random.choice(pids)
p = Process(pid)
process_name = p.name()
print(f"Name: {process_name}")

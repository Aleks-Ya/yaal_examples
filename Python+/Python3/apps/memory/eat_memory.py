# Consume all memory
import os
from datetime import datetime

import psutil

memory_usage_limit_megabytes = 1000


def memory_usage_megabytes():
    process = psutil.Process(os.getpid())
    mem = process.memory_info().rss / 1000000
    return mem


print("Start eating memory...")
print("Memory limit (MB): ", memory_usage_limit_megabytes)
big_list = []
last_memory_usage = 0
while last_memory_usage < memory_usage_limit_megabytes:
    big_list.append(datetime.now())
    if len(big_list) % 1000000 == 0:
        last_memory_usage = memory_usage_megabytes()
        print("Element count: ", len(big_list), ". Memory usage (MB): ", last_memory_usage)
print("The limit is exceeded")

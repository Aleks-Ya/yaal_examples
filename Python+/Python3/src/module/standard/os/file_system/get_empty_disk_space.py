# Get available disk space
import os

path = "/tmp"
stat = os.statvfs(path)
print(stat)

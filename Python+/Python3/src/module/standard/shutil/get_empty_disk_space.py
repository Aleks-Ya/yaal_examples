# Get available disk space
import shutil


def to_gb(size_bytes: int) -> int:
    return size_bytes // 1024 // 1024 // 1024


path = "/tmp"
usage = shutil.disk_usage(path)
total_gb = to_gb(usage.total)
free_gb = to_gb(usage.free)
used_gb = to_gb(usage.used)
print(f"Path: '{path}', Total: {total_gb}Gb, Used: {used_gb}Gb, Free: {free_gb}Gb")

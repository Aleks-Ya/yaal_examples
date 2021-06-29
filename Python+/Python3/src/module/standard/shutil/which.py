# Find executable by name
import shutil


def find(executable: str):
    path: str = shutil.which(executable)
    print(f"Path to '{executable}': '{path}'")


find('python')
find('winrm.cmd')
find('hdfs')
find('absent')

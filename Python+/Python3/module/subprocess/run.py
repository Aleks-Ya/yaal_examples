import subprocess

completed_process = subprocess.run(["ls", "-l"])

assert completed_process.returncode == 0


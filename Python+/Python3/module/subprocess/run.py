import subprocess

completed_process = subprocess.run(["ls", "-l"])

assert completed_process.returncode == 0
assert completed_process.args == ["ls", "-l"]
assert completed_process.stdout is None
assert completed_process.stderr is None

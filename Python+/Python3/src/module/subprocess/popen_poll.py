import subprocess
import time
from subprocess import PIPE

cmd = """
  cities=('Moscow' 'Spb');
  for city in "${cities[@]}"
  do
    echo $city
    sleep 2
  done
"""

# Using "poll()"
popen = subprocess.Popen(cmd, stdout=PIPE, stderr=PIPE, shell=True, executable="/bin/bash")
print(f"returncode: {popen.returncode}")
print(f"PID: {popen.pid}")
print(f"Stdin: {popen.stdin}")
assert popen.args == cmd
while True:
    exit_code = popen.poll()
    print(f"Exit code: {exit_code}")
    if exit_code is not None:
        break
    time.sleep(1)

print(f"Stdout: {popen.stdout.read()}")
print(f"Stderr: {popen.stderr.read()}")

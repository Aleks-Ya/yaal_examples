# Run subprocesses concurrently
import time
from subprocess import PIPE, Popen

cmd = """
  cities=('Moscow' 'Spb');
  for city in "${cities[@]}"
  do
    echo $city
    sleep 2
  done
"""

# Using "poll()"
process = Popen(cmd, stdout=PIPE, stderr=PIPE, shell=True, executable="/bin/bash")
print(f"returncode: {process.returncode}")
print(f"PID: {process.pid}")
print(f"Stdin: {process.stdin}")
assert process.args == cmd
while True:
    exit_code = process.poll()
    print(f"Exit code: {exit_code}")
    if exit_code is not None:
        break
    time.sleep(1)

print(f"Stdout: {process.stdout.read()}")
print(f"Stderr: {process.stderr.read()}")

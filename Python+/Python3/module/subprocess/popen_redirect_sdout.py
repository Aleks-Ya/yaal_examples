#  Redirect stdout and stderr of subprocess to current stdout.
import subprocess
import sys
import time

cmd = """
  cities=('Moscow' 'Spb');
  for city in "${cities[@]}"
  do
    echo $city
    sleep 2
  done
"""

popen = subprocess.Popen(cmd, stdout=sys.stdout, stderr=sys.stderr, shell=True, executable="/bin/bash")

assert popen.stdout is None
assert popen.stderr is None

while True:
    exit_code = popen.poll()
    print(f"Exit code: {exit_code}")
    if exit_code is not None:
        break
    time.sleep(1)

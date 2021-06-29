#  Redirect stdout and stderr of subprocess to current stdout.
import sys
import time
from subprocess import Popen

cmd = """
  cities=('Moscow' 'Spb');
  for city in "${cities[@]}"
  do
    echo $city
    sleep 2
  done
"""

process = Popen(cmd, stdout=sys.stdout, stderr=sys.stderr, shell=True, executable="/bin/bash")

assert process.stdout is None
assert process.stderr is None

while True:
    exit_code = process.poll()
    print(f"Exit code: {exit_code}")
    if exit_code is not None:
        break
    time.sleep(1)

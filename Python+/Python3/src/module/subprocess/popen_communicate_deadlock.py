# Popen.communicate should hang if stdout is overfilled
# Processes: 1
# NOT REPRODUCED
from subprocess import Popen, PIPE

repeats = 50000
big_string = 'BIG_OUTPUT_BIG_OUTPUT_BIG_OUTPUT_BIG_OUTPUT_BIG_OUTPUT_BIG_OUTPUT_BIG_OUTPUT_BIG_OUTPUT_BIG_OUTPUT'
cmd = "for n in {1..repeats}; do echo text; done" \
    .replace('repeats', str(repeats)) \
    .replace('text', big_string)
process = Popen(cmd, shell=True, executable="/bin/bash", stdout=PIPE, stderr=PIPE, bufsize=1024000)
outs, errs = process.communicate()
print('Finished')
out = outs.decode()
assert len(out) == (len(big_string) + 1) * repeats
print(f"Stdout length: {len(out)}")

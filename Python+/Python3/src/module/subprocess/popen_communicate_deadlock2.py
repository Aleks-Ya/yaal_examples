# Popen.communicate should hang if stdout is overfilled
# Processes: 2
# NOT REPRODUCED!!!
from subprocess import Popen, PIPE

repeats = 500000
big_string = 'BIG_OUTPUT_BIG_OUTPUT_BIG_OUTPUT_BIG_OUTPUT_BIG_OUTPUT_BIG_OUTPUT_BIG_OUTPUT_BIG_OUTPUT_BIG_OUTPUT'
cmd = "for n in {1..repeats}; do echo text; done" \
    .replace('repeats', str(repeats)) \
    .replace('text', big_string)
process1 = Popen(cmd, shell=True, executable="/bin/bash", stdout=PIPE, stderr=PIPE)
process2 = Popen(cmd, shell=True, executable="/bin/bash", stdout=PIPE, stderr=PIPE)
outs1, errs1 = process1.communicate()
outs2, errs2 = process2.communicate()
print('Finished')

out1 = outs1.decode()
assert len(out1) == (len(big_string) + 1) * repeats
print(f"Stdout 1 length: {len(out1)}")

out2 = outs2.decode()
assert len(out2) == (len(big_string) + 1) * repeats
print(f"Stdout 2 length: {len(out2)}")

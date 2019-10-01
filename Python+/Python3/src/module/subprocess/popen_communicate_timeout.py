from subprocess import Popen, TimeoutExpired, PIPE

cmd = "echo 'my stdout' && echo 'my error' > /dev/stderr && sleep 3"
timeout_sec = 1
process = Popen(cmd, shell=True, executable="/bin/bash", stdout=PIPE, stderr=PIPE)
outs: bytearray = bytearray()
errs: bytearray = bytearray()
killed = False
try:
    outs, errs = process.communicate(timeout=timeout_sec)
    print('success')
except TimeoutExpired:
    killed = True
    process.kill()
    outs, errs = process.communicate()
assert killed
assert outs.decode() == 'my stdout\n'
assert errs.decode() == 'my error\n'
assert process.returncode == -9

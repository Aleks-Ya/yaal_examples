from subprocess import PIPE, Popen, TimeoutExpired

cmd = """
        echo 'my std' && \
        echo 'my error' >> /dev/stderr && \
        exit 1"""
timeout_sec = 15
process = Popen(cmd, shell=True, executable="/bin/bash", stdout=PIPE, stderr=PIPE)
outs: bytearray = bytearray()
errs: bytearray = bytearray()
try:
    outs, errs = process.communicate(timeout=timeout_sec)
except TimeoutExpired:
    process.kill()
    outs, errs = process.communicate()
assert outs.decode() == 'my std\n'
assert errs.decode() == 'my error\n'
assert process.returncode == 1

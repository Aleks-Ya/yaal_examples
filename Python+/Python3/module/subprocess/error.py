import subprocess
from subprocess import PIPE, CalledProcessError

# Check error automatically
try:
    subprocess.run("exit 1", shell=True, check=True)
    assert False
except CalledProcessError as ex:
    assert str(ex) == "Command 'exit 1' returned non-zero exit status 1."

# Check error manually
completed_process = subprocess.run("exit 1", shell=True, stdout=PIPE, stderr=PIPE)
assert completed_process.returncode == 1
assert completed_process.args == "exit 1"
assert completed_process.stdout.decode("utf-8") == ''
assert completed_process.stderr.decode("utf-8") == ''

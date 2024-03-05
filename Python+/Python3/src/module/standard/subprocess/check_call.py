import subprocess
from subprocess import CalledProcessError

exit_code = subprocess.check_call(['echo'])
assert exit_code == 0

try:
    subprocess.check_call(['false'])
    assert False
except CalledProcessError as ex:
    assert str(ex) == "Command '['false']' returned non-zero exit status 1."

import subprocess

exit_code = subprocess.call(['ls', '-l'])
assert exit_code == 0

# With shell processing
exit_code = subprocess.call('echo $HOME', shell=True)
assert exit_code == 0

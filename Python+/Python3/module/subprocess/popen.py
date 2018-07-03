import subprocess

popen = subprocess.Popen("echo Hello World", stdout=subprocess.PIPE, shell=True)
print(popen.stdout.read())

import subprocess

print("Starting process")
proc = subprocess.Popen(["java", "hello.HelloWorld"], stdout=subprocess.PIPE, stderr=subprocess.PIPE)
stdout, stderr = proc.communicate()
print(stdout.decode())
print(stderr.decode())
print("Done")
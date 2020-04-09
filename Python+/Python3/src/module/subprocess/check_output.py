# Execute command and return it's output
from subprocess import check_output

cmd = ["echo", "123"]
output_byte_array = check_output(cmd)
output_string = output_byte_array.decode("utf-8")
assert output_string == "123\n"

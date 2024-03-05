import sys

print("Starting task")
print(f"All args: {sys.argv}")

for arg in sys.argv[1:]:
	print(f"Parse key-value pair: {arg}")
	split = arg.split("=")
	key = split[0]
	value = split[1]
	print(f"Set task value: key={key}, value={value}")
	dbutils.jobs.taskValues.set(key = key, value = value)

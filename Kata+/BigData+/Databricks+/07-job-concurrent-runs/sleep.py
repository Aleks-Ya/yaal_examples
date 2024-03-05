import sys
import time

print("Starting task")
print(f"All args: {sys.argv}")

sleep = int(sys.argv[1])
print(f"Sleeping for {sleep} seconds...")

time.sleep(sleep)

print("Done")

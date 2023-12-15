import time

# Milliseconds
current_time_millis = int(round(time.time() * 1000))
print(current_time_millis)

# Formatted
print(time.strftime('%H:%M:%S'))

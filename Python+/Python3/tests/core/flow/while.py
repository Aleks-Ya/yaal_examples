# Using "while" statement
import time


data = [1, 2, 3, 4]
index = 0
while data[index] < 3:
    print(data[index])
    index += 1

# Infinite loop
c = 0
while True:
    print(f"Infinite loop: {c}")
    time.sleep(2)
    c += 1
    if c > 3:
        break

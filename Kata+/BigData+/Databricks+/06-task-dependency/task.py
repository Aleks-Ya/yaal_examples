import sys

print("Starting task")
print(f"All args: {sys.argv}")

dividend = int(sys.argv[1])
print(f"Dividend: {dividend}")

divisor = int(sys.argv[2])
print(f"Divisor: {divisor}")

quotient = dividend / divisor
print(f"Quotient: {quotient}")

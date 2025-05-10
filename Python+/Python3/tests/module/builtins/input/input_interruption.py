# Interrupt input with Ctrl-C
# Run from Bash: python3 input_interruption.py
try:
    text: str = input("Enter text (press Ctrl-C to interrupt): ")
    print(f"You entered: {text}")
except KeyboardInterrupt:
    print("You interrupted the input!")

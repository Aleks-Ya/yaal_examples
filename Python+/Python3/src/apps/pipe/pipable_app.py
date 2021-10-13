# Run: echo "John" | python3 pipable_app.py |  echo "$(</dev/stdin) from Bash!"
import sys

data = sys.stdin.read().rstrip()
print(f"Hello, {data},")

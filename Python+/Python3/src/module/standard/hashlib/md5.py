# MD5 checksum
import hashlib

# Calculate MD5 for string
s: str = 'abc'
b: bytes = s.encode()
m = hashlib.md5(b)
digest: str = m.hexdigest()
assert digest == '900150983cd24fb0d6963f7d28e17f72'

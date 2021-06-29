import urllib.parse

origin = 'Hello HTTP'

# Encode to %20
encoded = urllib.parse.quote(origin)
assert encoded == 'Hello%20HTTP'

# Encode to +
encoded_plus = urllib.parse.quote_plus(origin)
assert encoded_plus == 'Hello+HTTP'

# Decode from %20
decoded = urllib.parse.unquote(encoded)
assert decoded == origin

# Decode from +
decoded_plus = urllib.parse.unquote_plus(encoded)
assert decoded_plus == origin

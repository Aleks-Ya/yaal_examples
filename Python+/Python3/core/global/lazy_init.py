# Lazy init a global variable
s = None


def lazy_value():
    global s
    if s is None:
        s = "hi"
    return s


assert s is None
assert lazy_value() == "hi"

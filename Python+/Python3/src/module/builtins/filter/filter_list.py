l = [1, -2, 3]
act = list(filter(lambda element: element > 0, l))
assert act == [1, 3]

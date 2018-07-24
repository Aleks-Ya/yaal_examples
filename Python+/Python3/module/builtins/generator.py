# Using generators

# Create a generator
g = (x * x for x in range(3))
for i in g:
    print(i)


# Create a generator by using "yield" operator
def generator():
    seq = range(3)
    for v in seq:
        yield v*v
        print("Hi")


g = generator()
print(g)
for i in g:
    print(i)

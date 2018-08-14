# Create a generator by using "yield" operator


def generator():
    seq = range(3)
    for v in seq:
        print("Before yield")
        res = v * v
        yield res
        print("After yield")


g = generator()
print('Generator: ', g)
for i in g:
    print('Item: ', i)

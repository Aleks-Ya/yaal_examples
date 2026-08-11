import matplotlib.pyplot as plt

x = [5, 7, 8, 7, 2, 17, 2, 9, 4, 11, 12, 9, 6]


def myfunc(x):
    return x * 2


mymodel = list(map(myfunc, x))

plt.plot(x, mymodel)
plt.show()

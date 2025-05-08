import matplotlib.pyplot as plt

x: list[int] = [5, 7, 8, 7, 2, 17, 2, 9, 4, 11, 12, 9, 6]
y: list[int] = [99, 86, 87, 88, 111, 86, 103, 87, 94, 78, 77, 85, 86]

plt.scatter(x, y)
plt.show()

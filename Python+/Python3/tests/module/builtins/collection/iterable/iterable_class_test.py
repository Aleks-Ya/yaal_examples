class MyIterable:
    def __init__(self, data):
        self.data = data

    def __iter__(self):
        self.index = 0
        return self

    def __next__(self):
        if self.index < len(self.data):
            result = self.data[self.index]
            self.index += 1
            return result
        else:
            raise StopIteration


def test_iterable():
    my_iterable: MyIterable = MyIterable([1, 2, 3])
    for item in my_iterable:
        print(item)

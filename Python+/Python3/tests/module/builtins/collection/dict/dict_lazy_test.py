from typing import Callable, Any


class LazyLambdaDict(dict):
    def __init__(self, lambda_dict: dict):
        super().__init__(lambda_dict)

    def __getitem__(self, key):
        return self.get(key)()

    def items(self):
        return [(k, self.get(k)()) for k in self.keys()]


def print_and_return(val):
    print('RETURN: ', val)
    return val


def test_lazy_dict():
    dict_src: dict[str, Callable[[], Any]] = {'one': lambda: print_and_return(1),
                                              'two': lambda: print_and_return(2)}
    print("ORIGIN DICT INITED")
    d2: LazyLambdaDict = LazyLambdaDict(dict_src)
    print("LAZY DICT INITED")
    print("GETTING ONE ITEM")
    print("ONE: ", d2['one'])
    print("ONE ITEM IS GOT")
    print("GETTING TWO ITEM")
    print("TWO: ", d2['two'])
    print("TWO ITEM IS GOT")
    print("CONTAINS: ", 'one' in d2)
    print("ITEMS: ", d2.items())

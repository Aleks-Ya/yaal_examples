class LowerStr(str):
    def __new__(cls, value):
        return super().__new__(cls, value.lower())

    def __init__(self, _):
        pass

    def __add__(self, other):
        return LowerStr(super().__add__(str(other)))


def test_lower_string():
    s: LowerStr = LowerStr('Hello')
    assert s == 'hello'
    assert s + ' World' == 'hello world'

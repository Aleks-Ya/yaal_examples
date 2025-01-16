def test_minimal_class_1():
    class MinimalClass1:
        """minimal class 1"""

    m1: MinimalClass1 = MinimalClass1()
    assert isinstance(m1, MinimalClass1)


def test_minimal_class_2():
    class MinimalClass2:
        pass

    m2: MinimalClass2 = MinimalClass2()
    assert isinstance(m2, MinimalClass2)


def test_constructor_has_no_arguments():
    class NoArgsConstructorClass:
        def __init__(self):
            self.data = 'hello'

    x: NoArgsConstructorClass = NoArgsConstructorClass()
    assert x.data == 'hello'


def test_constructor_with_arguments():
    class ArgsConstructorClass:
        def __new__(cls, *args, **kwargs):
            return super(ArgsConstructorClass, cls).__new__(cls)

        def __init__(self, text):
            self.text = text

    x: ArgsConstructorClass = ArgsConstructorClass('hi!')
    assert x.text == 'hi!'


def test_instantiate_class_without_initialization():
    class WithoutInitializationClass:
        def __init__(self, text):
            self.text = text

    without_initialization: WithoutInitializationClass = WithoutInitializationClass.__new__(WithoutInitializationClass)
    assert not hasattr(without_initialization, 'text')

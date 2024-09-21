class MyClass:
    def get_class_name(self) -> str:
        return self.__class__.__name__


def test_current_class_name():
    my_class: MyClass = MyClass()
    assert my_class.get_class_name() == "MyClass"

# Use parent class fields and methods


class ParentClass:
    def __init__(self):
        self.data = 'parent'
        self.city = 'msk'

    def get_data(self) -> str:
        return self.data


class DerivedClass(ParentClass):
    def __init__(self):
        super().__init__()

    def get_data(self) -> str:
        return super().get_data().upper()

    def get_city(self) -> str:
        return self.city


def test_parent_class_get_data():
    x: ParentClass = ParentClass()
    assert x.get_data() == 'parent'


def test_derived_class_get_data():
    x: DerivedClass = DerivedClass()
    assert x.get_data() == 'PARENT'


def test_derived_class_get_city():
    x: DerivedClass = DerivedClass()
    assert x.get_city() == 'msk'

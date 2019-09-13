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


x = ParentClass()
assert x.get_data() == 'parent'

x = DerivedClass()
assert x.get_data() == 'PARENT'
assert x.get_city() == 'msk'

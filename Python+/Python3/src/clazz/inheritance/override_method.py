# Override parent class members


class ParentClass:
    def __init__(self):
        self.data_parent = 'parent'

    def get_data(self) -> str:
        return self.data_parent


class DerivedClass(ParentClass):
    def __init__(self):
        super().__init__()
        self.data_derived = 'derived'

    def get_data(self) -> str:
        return self.data_derived


x = ParentClass()
assert x.get_data() == 'parent'

x = DerivedClass()
assert x.get_data() == 'derived'

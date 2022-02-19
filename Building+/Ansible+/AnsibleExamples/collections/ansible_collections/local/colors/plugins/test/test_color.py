class TestModule(object):

    def tests(self):
        return {
            'green': self.is_green
        }

    def is_green(self, color: str) -> bool:
        return color.lower() == 'green'


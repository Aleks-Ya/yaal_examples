from typing import List

class TestModule(object):

    def tests(self):
        return {
            'blue': self.is_blue
        }

    def is_blue(self, color: str) -> bool:
        return color.lower() == 'blue'


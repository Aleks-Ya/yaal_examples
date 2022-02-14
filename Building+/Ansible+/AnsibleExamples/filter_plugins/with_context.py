from jinja2.runtime import Context
from jinja2 import contextfilter

class FilterModule(object):

    def filters(self):
        return {
            'find_age': self.find_age,
            'find_age_absent': self.find_age_absent
        }

    @contextfilter
    def find_age(self, ctx:  Context, person_name: str):
        age: int = ctx.get('age')
        return f'{person_name} is {age} years old.'

    @contextfilter
    def find_age_absent(self, ctx:  Context, person_name: str):
        age: int = ctx.get('age_absent')
        return f'{person_name} is {age} years old.'

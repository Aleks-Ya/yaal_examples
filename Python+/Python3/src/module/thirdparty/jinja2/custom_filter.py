import jinja2
from jinja2 import Template, Environment


def greeting_format(value, greeting='Hello'):
    return f"{greeting}, {value}!"


env: Environment = jinja2.Environment()
env.filters["greeting_format"] = greeting_format

template: Template = env.from_string("{{ 'John' | greeting_format('Hi') }}")
render: str = template.render()

assert render == 'Hi, John!'

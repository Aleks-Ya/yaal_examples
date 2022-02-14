import jinja2
from jinja2 import Template, Environment

env: Environment = jinja2.Environment()
template: Template = env.from_string('Hello, {{ person }}!')
render: str = template.render(person='John')
assert render == 'Hello, John!'

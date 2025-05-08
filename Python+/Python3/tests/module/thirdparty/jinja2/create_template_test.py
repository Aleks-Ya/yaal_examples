import jinja2
from jinja2 import Template, Environment


def test_template_from_str():
    template: Template = Template('Hello, {{ name }}!')
    render: str = template.render(name='John Doe')
    assert render == 'Hello, John Doe!'


def test_get_template_from_env_var():
    env: Environment = jinja2.Environment()
    template: Template = env.from_string('Hello, {{ person }}!')
    render: str = template.render(person='John')
    assert render == 'Hello, John!'

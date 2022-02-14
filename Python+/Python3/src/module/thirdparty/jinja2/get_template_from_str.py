from jinja2 import Template

template: Template = Template('Hello, {{ name }}!')
render: str = template.render(name='John Doe')
assert render == 'Hello, John Doe!'

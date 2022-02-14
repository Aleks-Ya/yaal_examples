from jinja2.nodes import EvalContext
from jinja2 import evalcontextfilter

class FilterModule(object):

    def filters(self):
        return {
            'autoescape_status': self.autoescape_status
        }

    @evalcontextfilter
    def autoescape_status(self, eval_ctx:  EvalContext, person_name: str):
        return f'AutoEscape is {eval_ctx.autoescape}, {person_name}.'

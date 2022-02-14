from jinja2 import environmentfilter
from jinja2 import Environment

class FilterModule(object):

    def filters(self):
        return {
            'loader_info': self.loader_info
        }

    @environmentfilter
    def loader_info(self, env: Environment, person_name: str):
        return f"Loader is {type(env.loader).__name__}, {person_name}."

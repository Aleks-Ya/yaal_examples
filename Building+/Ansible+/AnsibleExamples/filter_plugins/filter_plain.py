from typing import List

class FilterModule(object):

    def filters(self):
        return {
            'person_greeting_hello': self.person_greeting_hello,
            'person_greeting_text': self.person_greeting_text,
            'person_greeting_text_exclamation': self.person_greeting_text_exclamation,
            'upper_case_list': self.upper_case_list,
            'str_upper_case': self.str_upper_case
        }

    def person_greeting_hello(self, person_name: str) -> str:
        return f'Hello, {person_name}!'

    def person_greeting_text(self, person_name: str, greeting_text: str) -> str:
        return f'{greeting_text}, {person_name}!'

    def person_greeting_text_exclamation(self, person_name: str, greeting_text: str, exclamation: str) -> str:
        return f'{greeting_text}, {person_name}{exclamation}'

    def upper_case_list(self, strings: List[str]) -> List[str]:
        return list(map(lambda s: s.upper(), strings))

    def str_upper_case(self,s: str) -> str:
        return s.upper()
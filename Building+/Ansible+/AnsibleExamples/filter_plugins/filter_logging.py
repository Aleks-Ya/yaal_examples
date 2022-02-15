from ansible.utils.display import Display

display = Display()

class FilterModule(object):

    def filters(self):
        return {
            'str_upper_case_and_log': self.str_upper_case_and_log
        }

    def str_upper_case_and_log(self, s: str) -> str:
        display.display("[display] Converting '%s' to upper case..." % s)
        display.v("[v] Converting '%s' to upper case..." % s)
        display.vv("[vv] Converting '%s' to upper case..." % s)
        display.vvv("[vvv] Converting '%s' to upper case..." % s)
        display.vvvv("[vvvv] Converting '%s' to upper case..." % s)
        display.error("[error] Converting '%s' to upper case..." % s)
        display.warning("[warning] Converting '%s' to upper case..." % s)
        display.debug("[debug] Converting '%s' to upper case..." % s)
        return s.upper()
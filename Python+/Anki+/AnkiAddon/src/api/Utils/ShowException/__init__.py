from logging import Logger

from aqt import mw
from aqt.errors import show_exception

from ._common import menu
from ._common.disable import enabled
from ._common.log import get_addon_logger

log: Logger = get_addon_logger()

if enabled():
    e: ArithmeticError = ArithmeticError("Hate math")
    show_exception(parent=mw, exception=e)

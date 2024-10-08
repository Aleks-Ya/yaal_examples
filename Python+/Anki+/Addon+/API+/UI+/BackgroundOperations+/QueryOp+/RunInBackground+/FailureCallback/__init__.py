import time

from anki.collection import Collection
from aqt import mw
from aqt.operations import QueryOp
from aqt.utils import show_info

from ._common.disable import enabled
from ._common import menu


def __background_op(_: Collection) -> int:
    time.sleep(2)
    a: int = 2
    if a > 1:
        raise ArithmeticError("I need a calculator")
    return 0


def __on_success(count: int) -> None:
    show_info(f"__background_op returned {count}")


def __on_failure(e: Exception) -> None:
    show_info(f'Failed with "{e}"')


def __ui_action() -> None:
    op: QueryOp = QueryOp(parent=mw, op=__background_op, success=__on_success)
    op.failure(__on_failure).run_in_background()


if enabled(True):
    menu.add_mw_menu_item("Start a failing QueryOp", __ui_action)

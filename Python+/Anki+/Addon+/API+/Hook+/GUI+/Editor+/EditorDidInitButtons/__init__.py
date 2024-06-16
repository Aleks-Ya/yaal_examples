from .add_custom_button import init
from .update_button_title_on_click import init2
from .add_disabled_button import init3
from ._common.disable import enabled

if enabled():
    init()
    init2()
    init3()

from aqt.mediacheck import check_media_db
from aqt import mw

from ._common.disable import enabled
from ._common import menu


def __on_event() -> None:
    check_media_db(mw)


if enabled():
    menu.add_mw_menu_item("Open the Check Media dialog", __on_event)

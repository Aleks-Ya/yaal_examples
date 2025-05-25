# Add items to "Main window -> Tools"
from aqt.qt import QMenu
from aqt import mw

from ._common.disable import enabled
from . import add_tag_to_note
from . import get_all_tags
from . import iterate_all_notes
from . import show_note_details
from . import show_collection_size

if enabled():
    parent_menu: QMenu = QMenu("Collection examples", mw)
    mw.form.menuTools.addMenu(parent_menu)

    show_note_details.add_menu_item(parent_menu)
    iterate_all_notes.add_menu_item(parent_menu)
    get_all_tags.add_menu_item(parent_menu)
    add_tag_to_note.add_menu_item(parent_menu)
    show_collection_size.add_menu_item(parent_menu)

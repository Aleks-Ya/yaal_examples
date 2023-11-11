# Add items to "Main window -> Tools"
from . import get_all_tags
from . import iterate_all_notes
from . import show_note_details

show_note_details.add_menu_item()
iterate_all_notes.add_menu_item()
get_all_tags.add_menu_item()

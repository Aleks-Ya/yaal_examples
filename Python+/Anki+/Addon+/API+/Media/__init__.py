# Show size of a sound attachment
from os.path import join, getsize

from anki.notes import NoteId, Note
from aqt import mw
from aqt.utils import showInfo

from ._common.disable import enabled
from ._common import menu


def __show_note_details() -> None:
    note_id: NoteId = NoteId(1638679140645)
    note: Note = mw.col.get_note(note_id)
    items_filtered: list[tuple[str, str]] = [item for item in note.items() if item[0] == "English-audio-generated"]
    item: tuple[str, str] = items_filtered[0]
    field_value: str = item[1]
    files: list[str] = mw.col.media.files_in_str(note.mid, field_value)
    media_dir: str = mw.col.media.dir()
    for file in files:
        full_path: str = join(media_dir, file)
        size: int = getsize(full_path)
        showInfo(f"NoteId={note.id}, field_value={field_value}, files={files}, file={file}, media_dir={media_dir}, "
                 f"full_path={full_path}, size={size}")


if enabled():
    menu.add_mw_menu_item("Show a sound size", __show_note_details)

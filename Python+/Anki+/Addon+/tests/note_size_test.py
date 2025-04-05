import os

from anki.collection import Collection
from anki.notes import Note


def test_note_size(col: Collection):
    content1: bytes = b'picture'
    content2: bytes = b'sound'
    content3: bytes = b'animation'
    filename1: str = col.media.write_data('picture.jpg', content1)
    filename2: str = col.media.write_data('sound.mp3', content2)
    filename3: str = col.media.write_data('animation.gif', content3)
    note: Note = col.newNote()
    front: str = f'Files: <img src="{filename1}"> <img src="{filename2}">'
    back: str = f'Files: <img src="{filename1}"> <img src="{filename3}">'
    note['Front'] = front
    note['Back'] = back
    col.addNote(note)
    act_size: int = __note_size(note)
    exp_size: int = len(front) + len(back) + len(content1) + len(content2) + len(content3)
    assert act_size == exp_size


def __note_size(note: Note) -> int:
    return __total_text_size(note) + __total_file_size(note)


def __total_text_size(note: Note):
    return sum([len(field) for field in note.fields])


def __total_file_size(note: Note) -> int:
    relative_names: set[str] = __all_files_in_note(note)
    full_names: set[str] = {os.path.join(note.col.media.dir(), filename) for filename in relative_names}
    return sum([os.path.getsize(full_name) for full_name in full_names])


def __all_files_in_note(note: Note) -> set[str]:
    all_files: set[str] = set[str]()
    for field in note.fields:
        files: list[str] = note.col.media.files_in_str(note.mid, field)
        all_files.update(files)
    return all_files

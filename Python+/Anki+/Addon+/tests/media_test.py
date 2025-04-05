import os.path
import textwrap
import timeit
from pathlib import Path
from typing import Sequence, Any

from anki.collection import Collection
from anki.media_pb2 import CheckMediaResponse
from anki.models import NotetypeId, NotetypeDict
from anki.notes import Note, NoteId


def test_add_file(col: Collection):
    tmp_file: Path = Path(col.media.dir()).joinpath('abc.txt')
    exp_content: str = "abc"
    tmp_file.write_text(exp_content)
    filename: str = col.media.add_file(tmp_file)
    assert col.media.have(filename)
    full_path: Path = Path(col.media.dir()).joinpath(filename)
    assert full_path.is_file()
    assert exp_content == full_path.read_text()


def test_write_data(col: Collection):
    exp_content: str = 'content'
    filename: str = col.media.write_data('abc.txt', exp_content.encode())
    assert col.media.have(filename)
    full_path: str = os.path.join(col.media.dir(), filename)
    assert os.path.isfile(full_path)
    with open(full_path, "r") as f:
        act_content: str = f.read()
    assert act_content == exp_content


def test_check(col: Collection):
    filename: str = col.media.write_data('abc.txt', b"abc")
    res: CheckMediaResponse = col.media.check()
    assert len(res.missing) == 0
    assert [filename] == list(res.unused)
    assert len(res.missing_media_notes) == 0
    assert not res.have_trash
    exp_report: str = textwrap.dedent("""\
    Missing files: ⁨0⁩
    Unused files: ⁨1⁩

    The following files were found in the media folder, but do not appear to be used on any cards:
    Unused: abc.txt
    """)
    assert exp_report == res.report


def test_attach_file_to_note(col: Collection):
    filename1: str = col.media.write_data('picture.jpg', b'picture')
    filename2: str = col.media.write_data('sound.mp3', b'sound')
    note: Note = col.newNote()
    front_field: str = 'Front'
    note[front_field] = f'Files: <img src="{filename1}"> <img src="{filename2}">'
    note['Back'] = 'two'
    col.addNote(note)
    note_id: NoteId = note.id
    note2: Note = col.get_note(note_id)
    front2: str = note2[front_field]
    mid2: NotetypeId = note2.mid
    files2: list[str] = col.media.files_in_str(mid2, front2)
    assert [filename1, filename2] == files2


def test_collect_all_files_in_note(col: Collection):
    filename1: str = col.media.write_data('picture.jpg', b'picture')
    filename2: str = col.media.write_data('sound.mp3', b'sound')
    filename3: str = col.media.write_data('animation.gif', b'animation')
    note: Note = col.newNote()
    note['Front'] = f'Files: <img src="{filename1}"> <img src="{filename2}">'
    note['Back'] = f'Files: <img src="{filename1}"> <img src="{filename3}">'
    col.addNote(note)
    note_id: NoteId = note.id
    note2: Note = col.get_note(note_id)
    all_files: set[str] = set()
    for field in note2.fields:
        mid2: NotetypeId = note2.mid
        files2: list[str] = col.media.files_in_str(mid2, field)
        all_files.update(files2)
    assert {filename1, filename2, filename3} == all_files


def test_extract_static_media_files(col: Collection):
    basic_note_type: str = 'Basic'
    model: NotetypeDict = col.models.by_name(basic_note_type)
    template: dict[str, Any] = model['tmpls'][0]
    template["qfmt"] = """{{Front}} <img src="_Lemon-Lime_Slush.jpg"> """
    template["afmt"] = """{{Back}} [sound:_googletts-25f19a6b-ea6e1f76-5a1be9f2-95124eac-06d072b2.mp3] """
    col.models.save(model)

    note_type_id: NotetypeId = col.models.id_for_name(basic_note_type)
    files: Sequence[str] = col.media.extract_static_media_files(note_type_id)
    assert files == ['_googletts-25f19a6b-ea6e1f76-5a1be9f2-95124eac-06d072b2.mp3', '_Lemon-Lime_Slush.jpg']


def test_files_in_str_performance(col: Collection):
    filename1: str = col.media.write_data('picture.jpg', b'picture')
    filename2: str = col.media.write_data('sound.mp3', b'sound')
    filename3: str = col.media.write_data('animation.gif', b'animation')
    note: Note = col.newNote()
    note['Front'] = f'Files: <img src="{filename1}"> <img src="{filename2}"> <img src="{filename3}">'
    col.addNote(note)
    execution_time_sec: float = timeit.timeit(lambda: col.media.files_in_str(note.mid, 'Front'), number=30_000)
    assert execution_time_sec < 1

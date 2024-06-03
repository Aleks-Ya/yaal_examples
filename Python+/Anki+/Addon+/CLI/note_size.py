import os
import tempfile
import unittest

from anki.collection import Collection
from anki.notes import Note


class NoteSizeTestCase(unittest.TestCase):

    def test_note_size(self):
        col: Collection = Collection(tempfile.mkstemp(suffix=".anki2")[1])
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
        act_size: int = NoteSizeTestCase._note_size(note)
        exp_size: int = len(front) + len(back) + len(content1) + len(content2) + len(content3)
        self.assertEqual(act_size, exp_size)
        col.close()

    @staticmethod
    def _note_size(note: Note) -> int:
        return NoteSizeTestCase._total_text_size(note) + NoteSizeTestCase._total_file_size(note)

    @staticmethod
    def _total_text_size(note: Note):
        return sum([len(field) for field in note.fields])

    @staticmethod
    def _total_file_size(note: Note) -> int:
        relative_names: set[str] = NoteSizeTestCase._all_files_in_note(note)
        full_names: set[str] = {os.path.join(note.col.media.dir(), filename) for filename in relative_names}
        return sum([os.path.getsize(full_name) for full_name in full_names])

    @staticmethod
    def _all_files_in_note(note: Note) -> set[str]:
        all_files: set[str] = set[str]()
        for field in note.fields:
            files: list[str] = note.col.media.files_in_str(note.mid, field)
            all_files.update(files)
        return all_files


if __name__ == '__main__':
    unittest.main()

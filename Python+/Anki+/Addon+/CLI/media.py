import os.path
import tempfile
import unittest

from anki.collection import Collection
from anki.media_pb2 import CheckMediaResponse
from anki.models import NotetypeId
from anki.notes import Note, NoteId


class MediaTestCase(unittest.TestCase):
    def setUp(self):
        self.col: Collection = Collection(tempfile.mkstemp(suffix=".anki2")[1])

    def test_add_file(self):
        tmp_file: str = os.path.join(self.col.media.dir(), 'abc.txt')
        exp_content: str = "abc"
        with open(tmp_file, 'w') as f:
            f.write(exp_content)
        filename: str = self.col.media.add_file(tmp_file)
        self.assertTrue(self.col.media.have(filename))
        full_path: str = os.path.join(self.col.media.dir(), filename)
        self.assertTrue(os.path.isfile(full_path))
        with open(full_path, "r") as f:
            act_content: str = f.read()
            self.assertEqual(act_content, exp_content)

    def test_write_data(self):
        exp_content: str = 'content'
        filename: str = self.col.media.write_data('abc.txt', exp_content.encode())
        self.assertTrue(self.col.media.have(filename))
        full_path: str = os.path.join(self.col.media.dir(), filename)
        self.assertTrue(os.path.isfile(full_path))
        with open(full_path, "r") as f:
            act_content: str = f.read()
        self.assertEqual(act_content, exp_content)

    def test_check(self):
        res: CheckMediaResponse = self.col.media.check()
        self.assertEqual(len(res.missing), 0)
        self.assertEqual(len(res.unused), 0)

    def test_attach_file_to_note(self):
        filename1: str = self.col.media.write_data('picture.jpg', b'picture')
        filename2: str = self.col.media.write_data('sound.mp3', b'sound')
        note: Note = self.col.newNote()
        front_field: str = 'Front'
        note[front_field] = f'Files: <img src="{filename1}"> <img src="{filename2}">'
        note['Back'] = 'two'
        self.col.addNote(note)
        note_id: NoteId = note.id
        note2: Note = self.col.get_note(note_id)
        front2: str = note2[front_field]
        mid2: NotetypeId = note2.mid
        files2: list[str] = self.col.media.files_in_str(mid2, front2)
        self.assertListEqual([filename1, filename2], files2)

    def test_collect_all_files_in_note(self):
        filename1: str = self.col.media.write_data('picture.jpg', b'picture')
        filename2: str = self.col.media.write_data('sound.mp3', b'sound')
        filename3: str = self.col.media.write_data('animation.gif', b'animation')
        note: Note = self.col.newNote()
        note['Front'] = f'Files: <img src="{filename1}"> <img src="{filename2}">'
        note['Back'] = f'Files: <img src="{filename1}"> <img src="{filename3}">'
        self.col.addNote(note)
        note_id: NoteId = note.id
        note2: Note = self.col.get_note(note_id)
        all_files: set[str] = set[str]()
        for field in note2.fields:
            mid2: NotetypeId = note2.mid
            files2: list[str] = self.col.media.files_in_str(mid2, field)
            all_files.update(files2)
        self.assertSetEqual({filename1, filename2, filename3}, all_files)

    def tearDown(self):
        self.col.close()


if __name__ == '__main__':
    unittest.main()

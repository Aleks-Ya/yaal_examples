import tempfile
import unittest
from typing import Dict, Any, Union, Optional, List

from anki.collection import Collection
from anki.models import ModelManager
from anki.notes import Note

from common.fields import synonym1_field, synonyms_field, antonym1_field, antonyms_field, english_field, \
    part_of_speech_field
from common.tags import absent_synonym1_tag, absent_synonyms_tag, absent_antonym1_tag, absent_antonyms_tag
from synonyms_antonyms.columns import english_column, pos_column, nid_column
from synonyms_antonyms.note_filler import fill_note


class NoteFiller2TestCase(unittest.TestCase):
    def setUp(self):
        self.col: Collection = Collection(tempfile.mkstemp()[1])
        mm: ModelManager = self.col.models
        self.note_type: dict[str, Any] = mm.new('English')
        field_names: list[str] = [english_field, part_of_speech_field, synonym1_field, synonyms_field, antonym1_field,
                                  antonyms_field]
        for field_name in field_names:
            field: dict[str, Any] = mm.new_field(field_name)
            mm.add_field(self.note_type, field)

        t: dict[str, Union[str, int, None]] = mm.new_template("Template1")
        t["qfmt"] = "{{" + english_field + "}}"
        t["afmt"] = "{{" + english_field + "}}"
        mm.add_template(self.note_type, t)
        mm.save(self.note_type)
        self.row_dark: Dict[str, str] = {nid_column: '1716012934072', english_column: 'dark', pos_column: 'Adjective',
                                         'Synonym 1': 'dim', 'Synonym 2': 'gloomy', 'Synonym 3': 'shadowy',
                                         'Synonym 4': 'obscure', 'Synonym 5': 'murky',
                                         'Antonym 1': 'light', 'Antonym 2': 'bright', 'Antonym 3': 'luminous',
                                         'Antonym 4': 'radiant', 'Antonym 5': 'clear'}

    def test_all_fields_empty(self):
        note: Note = self.createNote(english='dark')
        updated_note: Note = fill_note(note, self.row_dark)
        self.assertNote(updated_note, 'dark', '', 'dim', 'gloomy, shadowy, obscure, murky',
                        'light', 'bright, luminous, radiant, clear')

    def test_all_fields_have_absent_tag_empty(self):
        note: Note = self.createNote(english='dark',
                                     tags=[absent_synonym1_tag, absent_synonyms_tag, absent_antonym1_tag,
                                           absent_antonyms_tag])
        updated_note: Note = fill_note(note, self.row_dark)
        self.assertNote(updated_note, 'dark', '', 'dim', 'gloomy, shadowy, obscure, murky',
                        'light', 'bright, luminous, radiant, clear')

    def test_synonym1_field_empty(self):
        note: Note = self.createNote(english='dark', synonyms='dim, obscure, shadowy', antonym1='bright',
                                     antonyms='light, clear')
        updated_note: Note = fill_note(note, self.row_dark)
        self.assertNote(updated_note, 'dark', '', 'dim', 'obscure, shadowy',
                        'bright', 'light, clear')

    def test_synonym1_column_empty(self):
        note: Note = self.createNote(english='dark')
        self.row_dark['Synonym 1'] = ''
        updated_note: Note = fill_note(note, self.row_dark)
        self.assertNote(updated_note, 'dark', '', 'gloomy', 'shadowy, obscure, murky',
                        'light', 'bright, luminous, radiant, clear')

    def test_all_synonym_columns_empty(self):
        note: Note = self.createNote(english='dark')
        self.row_dark['Synonym 1'] = ''
        self.row_dark['Synonym 2'] = ''
        self.row_dark['Synonym 3'] = ''
        self.row_dark['Synonym 4'] = ''
        self.row_dark['Synonym 5'] = ''
        updated_note: Note = fill_note(note, self.row_dark)
        self.assertNote(updated_note, 'dark', '', '', '',
                        'light', 'bright, luminous, radiant, clear',
                        [absent_synonym1_tag, absent_synonyms_tag])

    def test_synonyms_field_empty(self):
        note: Note = self.createNote(english='dark', synonym1='obscure', antonym1='bright', antonyms='light, clear')
        updated_note: Note = fill_note(note, self.row_dark)
        self.assertNote(updated_note, 'dark', '', 'obscure', 'dim, gloomy, shadowy, murky',
                        'bright', 'light, clear')

    def test_synonym1_synonyms_fields_empty(self):
        note: Note = self.createNote(english='dark', antonym1='bright', antonyms='light, clear')
        updated_note: Note = fill_note(note, self.row_dark)
        self.assertNote(updated_note, 'dark', '', 'dim', 'gloomy, shadowy, obscure, murky',
                        'bright', 'light, clear')

    def test_antonym1_field_empty(self):
        note: Note = self.createNote(english='dark', synonym1='obscure', synonyms='dim, obscure, shadowy',
                                     antonyms='bright, light, clear')
        updated_note: Note = fill_note(note, self.row_dark)
        self.assertNote(updated_note, 'dark', '', 'obscure', 'dim, obscure, shadowy',
                        'light', 'bright, clear')

    def test_antonym1_column_empty(self):
        note: Note = self.createNote(english='dark')
        self.row_dark['Antonym 1'] = ''
        updated_note: Note = fill_note(note, self.row_dark)
        self.assertNote(updated_note, 'dark', '', 'dim', 'gloomy, shadowy, obscure, murky',
                        'bright', 'luminous, radiant, clear')

    def test_antonyms_field_empty(self):
        note: Note = self.createNote(english='dark', synonym1='obscure', synonyms='dim, obscure, shadowy',
                                     antonym1='bright')
        updated_note: Note = fill_note(note, self.row_dark)
        self.assertNote(updated_note, 'dark', '', 'obscure', 'dim, obscure, shadowy',
                        'bright', 'light, luminous, radiant, clear')

    def test_all_antonym_columns_empty(self):
        note: Note = self.createNote(english='dark')
        self.row_dark['Antonym 1'] = ''
        self.row_dark['Antonym 2'] = ''
        self.row_dark['Antonym 3'] = ''
        self.row_dark['Antonym 4'] = ''
        self.row_dark['Antonym 5'] = ''
        updated_note: Note = fill_note(note, self.row_dark)
        self.assertNote(updated_note, 'dark', '', 'dim', 'gloomy, shadowy, obscure, murky',
                        '', '', [absent_antonym1_tag, absent_antonyms_tag])

    def test_antonym1_antonyms_fields_empty(self):
        note: Note = self.createNote(english='dark', synonym1='obscure', synonyms='dim, obscure, shadowy')
        updated_note: Note = fill_note(note, self.row_dark)
        self.assertNote(updated_note, 'dark', '', 'obscure', 'dim, obscure, shadowy',
                        'light', 'bright, luminous, radiant, clear')

    def test_english_field_differs_from_column(self):
        with self.assertRaises(RuntimeError) as ctx:
            note: Note = self.createNote(english='famous')
            fill_note(note, self.row_dark)
        self.assertEqual(str(ctx.exception), 'Wrong English word: note=famous, row=dark')

    def test_old_fields_are_already_filled(self):
        with self.assertRaises(RuntimeError) as ctx:
            note: Note = self.createNote(english='dark', synonym1='murky', synonyms='dim,obscure',
                                         antonym1='bright', antonyms='light,clear')
            fill_note(note, self.row_dark)
        self.assertEqual(str(ctx.exception), f"All fields are filled already: nid={note.id}, Synonym1='murky',"
                                             " Synonyms='dim,obscure', Antonym1='bright', Antonyms='light,clear'")

    def createNote(self, english: Optional[str] = None, synonym1: Optional[str] = None, synonyms: Optional[str] = None,
                   antonym1: Optional[str] = None, antonyms: Optional[str] = None,
                   tags: Optional[List[str]] = None) -> Note:
        note: Note = self.col.new_note(self.note_type)
        if english:
            note[english_field] = english
        if synonym1:
            note[synonym1_field] = synonym1
        if synonyms:
            note[synonyms_field] = synonyms
        if antonym1:
            note[antonym1_field] = antonym1
        if antonyms:
            note[antonyms_field] = antonyms
        if tags:
            note.tags = tags
        self.col.addNote(note)
        return note

    def assertNote(self, note: Note, english: str, pos: str, synonym1: str, synonyms: str, antonym1: str,
                   antonyms: str, tags: List[str] = None):
        if tags is None:
            tags = list()
        print(f"Fields: {note.items()}")
        print(f"Tags: {note.tags}")
        self.assertListEqual([
            (english_field, english),
            (part_of_speech_field, pos),  # TODO verify that POS and ID in Note and Row are equal
            (synonym1_field, synonym1),
            (synonyms_field, synonyms),
            (antonym1_field, antonym1),
            (antonyms_field, antonyms)
        ],
            note.items())
        self.assertListEqual(tags, note.tags)

    def tearDown(self):
        self.col.close()


if __name__ == '__main__':
    unittest.main()

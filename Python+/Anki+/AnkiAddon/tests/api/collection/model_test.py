from typing import Optional, Sequence, Any

import pytest
from anki.collection import Collection
from anki.decks import DeckId
from anki.errors import InvalidInput
from anki.models import FieldDict, NotetypeId, NoteType, NotetypeNameId, TemplateDict, ModelManager, NotetypeDict
from anki.notes import Note, NoteId

basic_model_name: str = 'Basic'


def test_get_all_models(col: Collection, basic_note_type: NoteType, cloze_note_type: NoteType):
    all_models: list[NoteType] = col.models.all()
    assert len(all_models) == 6
    assert basic_note_type in all_models
    assert cloze_note_type in all_models


def test_all_names(col: Collection, basic_note_type: NoteType, cloze_note_type: NoteType):
    all_names: list[str] = col.models.all_names()
    assert all_names == ['Basic', 'Basic (and reversed card)', 'Basic (optional reversed card)',
                         'Basic (type in the answer)', 'Cloze', 'Image Occlusion']
    assert basic_note_type['name'] in all_names
    assert cloze_note_type['name'] in all_names


def test_get_all_names_and_ids(col: Collection, basic_note_type: NoteType, cloze_note_type: NoteType):
    all_name_ids: Sequence[NotetypeNameId] = col.models.all_names_and_ids()
    print(all_name_ids)
    assert len(all_name_ids) == 6


def test_get_model_by_name(col: Collection, basic_note_type: NoteType):
    basic_model: Optional[NoteType] = col.models.by_name(basic_model_name)
    assert basic_model['name'] == basic_model_name
    assert basic_model == basic_note_type


def test_get(col: Collection, basic_note_type: NoteType):
    note: Note = col.new_note(basic_note_type)
    model_id: NotetypeId = note.mid
    act_note_type: NoteType = col.models.get(model_id)
    assert act_note_type == basic_note_type


def test_get_single_note_type_of_notes(col: Collection, basic_note_type: NoteType, deck_id: DeckId):
    note_1: Note = col.new_note(basic_note_type)
    note_2: Note = col.new_note(basic_note_type)
    col.add_note(note_1, deck_id)
    col.add_note(note_2, deck_id)
    note_ids: Sequence[NoteId] = [note_1.id, note_2.id]
    single_note_type_id: NotetypeId = col.models.get_single_notetype_of_notes(note_ids)
    single_note_type: NoteType = col.models.get(single_note_type_id)
    assert single_note_type == basic_note_type


def test_get_single_note_type_of_notes_invalid_input(col: Collection, basic_note_type: NoteType,
                                                     cloze_note_type: NoteType, deck_id: DeckId):
    note_1: Note = col.new_note(basic_note_type)
    note_2: Note = col.new_note(cloze_note_type)
    col.add_note(note_1, deck_id)
    col.add_note(note_2, deck_id)
    note_ids: Sequence[NoteId] = [note_1.id, note_2.id]
    with pytest.raises(InvalidInput):
        col.models.get_single_notetype_of_notes(note_ids)


def test_id_for_name(col: Collection):
    note_type_id: Optional[NotetypeId] = col.models.id_for_name("Basic")
    assert note_type_id


def test_field_names(col: Collection):
    basic_model: Optional[NoteType] = col.models.by_name(basic_model_name)
    field_names: list[str] = col.models.field_names(basic_model)
    assert field_names == ['Front', 'Back']


def test_add_new_field(col: Collection):
    mm: ModelManager = col.models
    basic_model: Optional[NoteType] = mm.by_name(basic_model_name)
    field: FieldDict = mm.new_field('Comment')
    mm.add_field(basic_model, field)
    assert mm.field_names(basic_model) == ['Front', 'Back', 'Comment']
    mm.save(basic_model)
    new_note: Note = col.new_note(basic_model)
    new_note['Comment'] = 'comment'


def test_modify_template(col: Collection):
    mm: ModelManager = col.models
    model: NoteType = mm.by_name(basic_model_name)
    templates: list[TemplateDict] = model['tmpls']
    template: TemplateDict = templates[0]
    question_template: str = template["qfmt"]
    answer_template: str = template["afmt"]
    assert question_template == '{{Front}}'
    assert answer_template == """{{FrontSide}}\n\n<hr id=answer>\n\n{{Back}}"""
    new_question_format: str = "My question: {{Front}}"
    new_answer_format: str = "Your answer: {{Back}}"
    template["qfmt"] = new_question_format
    template["afmt"] = new_answer_format
    mm.save(model)

    act_template: dict[str, Any] = mm.by_name(basic_model_name)['tmpls'][0]
    assert act_template["qfmt"] == new_question_format
    assert act_template["afmt"] == new_answer_format


def test_create_note_type(col: Collection):
    mm: ModelManager = col.models
    note_type_name: str = "My Note Type"
    note_type: NotetypeDict = mm.new(note_type_name)
    field_front: FieldDict = mm.new_field('Front')
    field_back: FieldDict = mm.new_field('Back')
    mm.add_field(note_type, field_front)
    mm.add_field(note_type, field_back)

    t: TemplateDict = mm.new_template("Reverse")
    t["qfmt"] = "{{Back}}"
    t["afmt"] = "{{Front}}"
    mm.add_template(note_type, t)

    mm.save(note_type)
    act_note_type: NotetypeDict = mm.by_name(note_type_name)
    assert act_note_type['name'] == note_type_name


def test_rename_note_type(col: Collection):
    mm: ModelManager = col.models
    new_name: str = "My Note Type"
    assert mm.by_name(new_name) is None
    note_type: NoteType = mm.by_name(basic_model_name)
    assert note_type['name'] == basic_model_name
    note_type['name'] = new_name
    mm.save(note_type)
    assert mm.by_name(new_name) is not None

import tempfile
from typing import Optional, Sequence, Any

import pytest
from anki.collection import Collection
from anki.models import FieldDict, NotetypeId, NoteType
from anki.notetypes_pb2 import NotetypeNameId


@pytest.fixture
def col():
    col: Collection = Collection(tempfile.mkstemp(suffix=".anki2")[1])
    yield col
    col.close()


basic_model_name: str = 'Basic'


def test_get_all_models(col: Collection):
    all_models: list[NoteType] = col.models.all()
    assert len(all_models) == 6


def test_get_model_by_name(col: Collection):
    basic_model: Optional[NoteType] = col.models.by_name(basic_model_name)
    assert basic_model['name'] == basic_model_name


def test_get_all_model_names(col: Collection):
    names: list[str] = col.models.all_names()
    assert names == [basic_model_name, 'Basic (and reversed card)', 'Basic (optional reversed card)',
                     'Basic (type in the answer)', 'Cloze', 'Image Occlusion']


def test_get_all_model_names_and_ids(col: Collection):
    names_ids: Sequence[NotetypeNameId] = col.models.all_names_and_ids()
    assert len(names_ids) == 6


def test_id_for_name(col: Collection):
    note_type_id: Optional[NotetypeId] = col.models.id_for_name("Basic")
    assert note_type_id


def test_get_field_names(col: Collection):
    basic_model: Optional[NoteType] = col.models.by_name(basic_model_name)
    field_names: list[str] = col.models.field_names(basic_model)
    assert field_names == ['Front', 'Back']


def test_add_new_field(col: Collection):
    basic_model: Optional[NoteType] = col.models.by_name(basic_model_name)
    field: FieldDict = col.models.new_field('Comment')
    col.models.add_field(basic_model, field)
    assert col.models.field_names(basic_model) == ['Front', 'Back', 'Comment']


def test_modify_template(col: Collection):
    model: NoteType = col.models.by_name(basic_model_name)
    templates: list[dict[str, Any]] = model['tmpls']
    template: dict[str, Any] = templates[0]
    question_template: str = template["qfmt"]
    answer_template: str = template["afmt"]
    assert question_template == '{{Front}}'
    assert answer_template == """{{FrontSide}}\n\n<hr id=answer>\n\n{{Back}}"""
    new_question_format: str = "My question: {{Front}}"
    new_answer_format: str = "Your answer: {{Back}}"
    template["qfmt"] = new_question_format
    template["afmt"] = new_answer_format
    col.models.save(model)

    act_template: dict[str, Any] = col.models.by_name(basic_model_name)['tmpls'][0]
    assert act_template["qfmt"] == new_question_format
    assert act_template["afmt"] == new_answer_format

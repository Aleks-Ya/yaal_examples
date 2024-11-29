from typing import Optional

from anki.collection import Collection
from anki.models import ModelManager, NoteType, TemplateDict


def test_get_note_type_templates(col: Collection):
    note_type: NoteType = col.models.by_name('Basic')
    templates: TemplateDict = note_type["tmpls"]
    assert len(templates) == 1


def test_create_template(col: Collection):
    mm: ModelManager = col.models
    note_type: Optional[NoteType] = mm.by_name('Basic')
    t: TemplateDict = mm.new_template("Reverse")
    t["qfmt"] = "{{Back}}"
    t["afmt"] = "{{Front}}"
    mm.add_template(note_type, t)
    templates: TemplateDict = note_type["tmpls"]
    assert len(templates) == 2

# Anki add-on.
# 1. Adds "Fill "PartOfSpeech" field from tag" item in "Main menu"/"Tools".
# 2. On click, finds all notes having part of speech tags ("en::parts::noun", etc.)
#    and fill "PartOfSpeech-generated" field with appropriate name ("Noun", etc.).
import logging
import os
from pathlib import Path
from typing import List, Dict, Sequence

from anki.models import NoteType
from anki.notes import NoteId, Note
from aqt import mw
from aqt.qt import QAction, qconnect
from aqt.utils import showInfo

init_py_file: Path = Path(__file__)
addon_dir: Path = init_py_file.parent
addon_name: str = addon_dir.name
log_file = os.path.join(addon_dir, f"{addon_name}.log")
log: logging.Logger = logging.getLogger(addon_name)
log.setLevel(logging.DEBUG)
handler: logging.FileHandler = logging.FileHandler(log_file)
handler.setLevel(logging.DEBUG)
handler.setFormatter(logging.Formatter('%(asctime)s %(name)s %(funcName)s %(levelname)s %(message)s'))
log.addHandler(handler)
log.info(f'Logger is configured: file={log_file}')


def _fill() -> None:
    tag_separator: str = '::'
    base_tag: str = 'en::parts'
    all_tags: List[str] = mw.col.tags.all()
    parts_tags: List[str] = [tag for tag in all_tags if tag.startswith(base_tag)]
    tag_to_name: Dict[str, str] = {}
    for tag in parts_tags:
        split: List[str] = tag.split(tag_separator)
        part_sub_tag: str = split[2]
        part_tag: str = base_tag + tag_separator + part_sub_tag
        part_name: str = part_sub_tag.capitalize()
        tag_to_name[part_tag] = part_name
    log.info(f"Tag to Name map: {tag_to_name}")

    pos_field: str = 'PartOfSpeech-generated'
    note_type_names: List[str] = ['En-word-or-sentence']
    stat_notes_scanned: int = 0
    stat_notes_updated: int = 0
    for tag in tag_to_name:
        note_ids: Sequence[NoteId] = mw.col.find_notes(f"tag:{tag}*")
        log.info(f"Found notes for tag: tag={tag}, notes={len(note_ids)}")
        stat_notes_scanned = stat_notes_scanned + len(note_ids)
        for note_id in note_ids:
            note: Note = mw.col.get_note(note_id)
            note_type: NoteType = mw.col.models.get(note.mid)
            note_type_name = note_type['name']
            if note_type_name in note_type_names:
                pos_actual: str = note[pos_field] if pos_field in note else ""
                pos_target: str = tag_to_name[tag]
                if pos_actual != pos_target:
                    log.info(f"Updating note: nid={note_id}, pos_actual={pos_actual},pos_target={pos_target}")
                    note[pos_field] = pos_target
                    mw.col.update_note(note)
                    stat_notes_updated = stat_notes_updated + 1
    showInfo(f"Finished: notes scanned={stat_notes_scanned}, notes updated={stat_notes_updated}")


action = QAction('Fill "PartOfSpeech" field from tag', mw)
qconnect(action.triggered, _fill)
mw.form.menuTools.addAction(action)

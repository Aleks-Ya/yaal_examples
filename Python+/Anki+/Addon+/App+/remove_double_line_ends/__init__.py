# Anki add-on.
# 1. Adds "Clean generated fields" item in "Main menu"/"Tools".
# 2. On click, perform find by regex and replace in specified note fields.
import logging
import os
import re
from pathlib import Path
from typing import Dict, Sequence, List

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


def _remove() -> None:
    replacements: Dict[str, str] = {
        r"<br>\s*<div": "<div",
        r"</div>\s*<br>": "</div>",
        r"<br>\s*</div>": "</div>",
        r"</li>\s*<br>": "</li>",
        r"<br>\s*<li>": "<li>",
        r"<br>\s*<ol>": "<ol>",
        r"</ol>\s*<br>": "</ol>",
        r"<h5>近义词[:：]?</h5>": "<div>Synonyms:</div>",
        r"<h5>反义词[:：]?</h5>": "<div>Antonyms:</div>",
        r"<h5>联想词</h5>": "<div>Associated words:</div>",
        r"<h5>解析[:：]?</h5>": "<div>Analysis:</div>"
    }
    field_names: List[str] = [
        'Examples2-generated',
        'Synonyms-generated',
        'Gpt4Short',
        'Gpt4Long',
        'Bard',
        'Quote'
    ]
    scanned_notes_counter: int = 0
    updated_notes_counter: int = 0
    for field_name in field_names:
        note_ids: Sequence[NoteId] = mw.col.find_notes(f"{field_name}:_*")
        log.info(f"Found notes: field={field_name}, notes={len(note_ids)}")
        scanned_notes_counter = scanned_notes_counter + len(note_ids)
        for note_id in note_ids:
            note: Note = mw.col.get_note(note_id)
            old_value: str = note[field_name]
            new_value: str = old_value
            for regex, replacement in replacements.items():
                new_value: str = re.sub(regex, replacement, new_value)
            if new_value != old_value:
                log.info(f"Updating note: {note_id}")
                log.info(f"old_value:\n{old_value}")
                log.info(f"new_value:\n{new_value}")
                note[field_name] = new_value
                mw.col.update_note(note)
                updated_notes_counter = updated_notes_counter + 1
    showInfo(f"Finished: notes scanned={scanned_notes_counter}, notes updated={updated_notes_counter}")


action = QAction('Clean generated fields', mw)
qconnect(action.triggered, _remove)
mw.form.menuTools.addAction(action)

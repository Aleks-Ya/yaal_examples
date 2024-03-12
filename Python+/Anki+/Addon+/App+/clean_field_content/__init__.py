# Anki add-on.
# 1. Adds "Clean generated fields" item in "Main menu"/"Tools".
# 2. On click, perform find by regex and replace in specified note fields.
import logging
import os
import re
from pathlib import Path
from typing import Dict, Sequence, List

from anki.collection import OpChanges, Collection, OpChangesWithCount
from anki.notes import NoteId, Note
from aqt import mw
from aqt.operations import CollectionOp, ResultWithChanges
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

dry_run: bool = False


def _background_operation(col: Collection) -> ResultWithChanges:
    changes = OpChangesWithCount()
    finished: bool = False
    while not finished:
        finished = _remove(changes, col)
    return changes


def _remove(changes, col) -> bool:
    log.info(f"Iteration start: changes={changes.count}")
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
        r"<h5>解析[:：]?</h5>": "<div>Analysis:</div>",
        r"&nbsp;": " ",
        r"<em><em>": "<em>",
        r"</em></em>": "</em>",
        r'<div\s*class="sen_cn">.*</div>': "",  # Chinese characters
        r"[\u4e00-\u9fff\u3400-\u4dbf\u2e80-\u2eff\u3000-\u303f\uff00-\uffef]": "",  # Chinese characters
        r'<br>\s*<br>\s*<i>': "<br><i>",
        r'<br/>\s*<br/>\s*<i>': "<br/><i>",
        r"<div></div>": "",
        r"“.*”": "",
        r"‘.*’": "",
        r"”.*“": "",
        r"—": "",
        r'<div\s*class="sen_en">\d{1,2}\.': '<div class="sen_en">',
        r"<br>\s*<br>\s*</div>": "<br></div>",
        r"<div>\s*<br>\s*</div>": "",
        r"<br>\s*<h2>": "<h2>",
        r"</h2>\s*<br>": "</h2>",
        r"<br>\s*<ul>": "<ul>",
        r"</ul>\s*<br>": "</ul>"
    }
    field_names: List[str] = [
        'Examples1-generated',
        'Examples2-generated',
        'Synonyms-generated',
        'Gpt4Short',
        'Gpt4Long',
        'Bard',
        'Quote',
        'Description-my',
        'Synonym1',
        'Synonyms',
        'Antonyms',
        'Answer',
        'Example-my',
        'Transcription',
        'Documentation',
        'Question'
    ]
    updated_count: int = 0
    for field_name in field_names:
        note_ids: Sequence[NoteId] = col.find_notes(f"{field_name}:_*")
        log.info(f"Found notes: field={field_name}, notes={len(note_ids)}")
        for note_id in note_ids:
            note: Note = col.get_note(note_id)
            old_value: str = note[field_name]
            new_value: str = old_value
            for regex, replacement in replacements.items():
                new_value = re.sub(regex, replacement, new_value).strip()
            if new_value != old_value:
                log.info(f"Updating note: {note_id}")
                log.info(f"Field {field_name} old value:\n{old_value}")
                log.info(f"Field {field_name} new value:\n{new_value}")
                note[field_name] = new_value
                if not dry_run:
                    col.update_note(note)
                else:
                    log.warning(f"Skipped update because dry_run={dry_run}")
                changes.count += 1
                updated_count += 1
            mw.taskman.run_on_main(
                lambda: mw.progress.update(label=f"Updated: {changes.count}")
            )
            if mw.progress.want_cancel():
                log.info(f"Cancelling")
                return True
    return updated_count == 0


def on_success(changes: ResultWithChanges) -> None:
    dry_run_str: str = " (DRY RUN)" if dry_run else ""
    showInfo(f"Finished: updated {changes.count} notes{dry_run_str}")


def on_failure(e: Exception) -> None:
    showInfo(f"Failed: {e}")


def _ui_action():
    op: CollectionOp[OpChanges] = CollectionOp(parent=mw, op=lambda col: _background_operation(col))
    op.success(on_success)
    op.failure(on_failure)
    op.run_in_background()


action = QAction('Clean fields', mw)
qconnect(action.triggered, _ui_action)
mw.form.menuTools.addAction(action)

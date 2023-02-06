import logging
from logging import Logger, FileHandler, Formatter
from pathlib import Path
from typing import Dict, List, Sequence

import aqt.qt
from anki.notes import NoteId, Note
from aqt import mw

init_py_file: Path = Path(__file__)
addon_dir: Path = init_py_file.parent
addon_name: str = addon_dir.name

logger: Logger = logging.getLogger(addon_name)
logger.setLevel(logging.DEBUG)

log_file: Path = Path(addon_dir, addon_name + '.log')
handler: FileHandler = FileHandler(log_file)
handler.setLevel(logging.DEBUG)
handler.setFormatter(Formatter('%(asctime)s %(levelname)s %(message)s'))
logger.addHandler(handler)

V1_TEXT_FIELD = 'v1_text'
ING_TEXT_FIELD = 'ing_text'
S_TEXT_FIELD = 's_text'


def fill():
    logger.info('Start')
    file: Path = Path(addon_dir, 'verbs.csv')

    with open(file, newline='') as csv_file:
        import csv
        reader = csv.reader(csv_file, delimiter=',')
        verbs: Dict[str, List[str]] = dict()
        for row in reader:
            verbs[row[0]] = row
    logger.info("Dict Size: %d" % len(verbs))
    note_ids: Sequence[NoteId] = mw.col.find_notes('note:En-irregular-verb ing_text:')
    logger.info("Found note_ids: %d", len(note_ids))
    for note_id in note_ids:
        note: Note = mw.col.get_note(note_id)
        logger.info("Note items: %s" % note.items())
        v1_text: str = note[V1_TEXT_FIELD]
        ing_text_field: str = note[ING_TEXT_FIELD]
        if v1_text in verbs:
            verb_row = verbs[v1_text]
            if not ing_text_field:
                ing_text_target: str = verb_row[3]
                if ing_text_target:
                    note[ING_TEXT_FIELD] = ing_text_target
                    mw.col.update_note(note)
                    logger.info("Updated ING: v1='%s', ING='%s'", v1_text, ing_text_target)
                else:
                    logger.warning("Not found target ING for '%s'", v1_text)
            s_text_field: str = note[S_TEXT_FIELD]
            if not s_text_field:
                s_text_target: str = verb_row[4]
                if s_text_target:
                    note[S_TEXT_FIELD] = s_text_target
                    mw.col.update_note(note)
                    logger.info("Updated S: v1='%s', ING='%s'", v1_text, s_text_target)
                else:
                    logger.warning("Not found target S for '%s'", v1_text)
        else:
            logger.warning("Not found target row for '%s'", v1_text)
    logger.info('Done')


action = aqt.qt.QAction("Fill irregular verbs", mw)
action.triggered.connect(fill)
mw.form.menuTools.addAction(action)

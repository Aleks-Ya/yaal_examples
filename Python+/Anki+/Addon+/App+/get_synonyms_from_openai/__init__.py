# Anki add-on.
# 1. Adds "Clean generated fields" item in "Main menu"/"Tools".
# 2. On click, perform find by regex and replace in specified note fields.
import logging
import os
import sys
from csv import DictReader
from io import StringIO
from pathlib import Path
from typing import Sequence, List

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

addon_dir = os.path.dirname(__file__)
sys.path.insert(1, os.path.abspath(os.path.dirname(__file__)))
sys.path.insert(1, os.path.join(addon_dir, 'bundled_dependencies'))

log.info(f"sys.path={sys.path}")

from openai import OpenAI
from openai.types.chat import ChatCompletion

word_field: str = 'English'
part_of_speech_field: str = 'PartOfSpeech-generated'
synonym1_field: str = 'Synonym1'
synonyms_field: str = 'Synonyms'
absent_synonym1_tag: str = '~api::absent::synonym1'
absent_synonyms_tag: str = '~api::absent::synonyms'


def _fill() -> None:
    query: str = (f'(({synonym1_field}: -tag:{absent_synonym1_tag}) OR ({synonyms_field}: -tag:{absent_synonyms_tag})) '
                  f'-tag:en::unit*')
    log.info(f"Query: '{query}'")
    note_ids: Sequence[NoteId] = mw.col.find_notes(query)
    log.info(f"Found notes: {len(note_ids)}")
    notes_to_update: List[Note] = []
    for note_id in note_ids:
        note: Note = mw.col.get_note(note_id)
        synonym1_old: str = note[synonym1_field]
        synonyms_old: str = note[synonyms_field]
        need_update: bool = not synonym1_old or not synonyms_old
        if need_update:
            notes_to_update.append(note)
    log.info(f"Notes to update: {len(notes_to_update)}")
    notes_limit: int = 50

    for i in range(0, len(notes_to_update), notes_limit):
        log.info(f"Processed notes: {i} from {len(notes_to_update)}")
        sublist = notes_to_update[i:i + notes_limit]
        _update_notes(sublist)
    showInfo(f"Finished")


def _update_notes(notes_to_update):
    log.info(f"Notes to update (limited): {len(notes_to_update)}")
    lines: List[str] = []
    for note in notes_to_update:
        word: str = note[word_field]
        if not note[part_of_speech_field]:
            raise RuntimeError(f"Part of speech is missing: nid={note.id}")
        pos: str = note[part_of_speech_field]
        line: str = f'"{note.id}","{word}","{pos}"'
        lines.append(line)
    lines_str: str = '\n'.join(lines)
    nid_column: str = 'ID'
    english_column: str = 'English Word'
    pos_column: str = 'Part Of Speech'
    synonym_number: int = 5
    synonym_headers: List[str] = [f'Synonym {i}' for i in range(1, synonym_number + 1)]
    log.debug(f"synonym_headers: {synonym_headers}")
    synonym_headers_quoted: List[str] = [f'"{synonym_header}"' for synonym_header in synonym_headers]
    log.debug(f"synonym_headers_quoted: {synonym_headers_quoted}")
    prompt: str = (
        f'I will provide you a CSV snippet. '
        f'You need to fill columns {", ".join(synonym_headers_quoted)} in the snippet. '
        f'Use the most simple and wide-spread synonyms if possible. '
        f'One-word synonyms are preferred over multi-words synonyms. '
        f'Your response must contain strictly only raw CSV content. '
        f'The CSV snippet is:\n'
        f'```\n'
        f'"{nid_column}","{english_column}","{pos_column}",{",".join(synonym_headers_quoted)}\n'
        f'{lines_str}\n'
        f'```\n'
    )
    log.debug(f"Prompt:\n{prompt}")
    key_file: Path = Path.home().joinpath('.gpt/token.txt')
    with open(key_file) as f:
        key: str = f.read()
    client: OpenAI = OpenAI(api_key=key)
    timeout_sec: float = 60
    chat_completion: ChatCompletion = client.chat.completions.create(
        messages=[
            {
                "role": "user",
                "content": prompt,
            }
        ],
        model="gpt-4-1106-preview",
        timeout=timeout_sec
    )
    message: str = chat_completion.choices[0].message.content
    log.debug(f"Message:\n{message}")
    message = message.replace('```\n', '').replace('\n```', '')
    log.debug(f"Message without MarkDown:\n{message}")
    with StringIO(message) as csv_file:
        reader: DictReader = DictReader(csv_file, doublequote=True)
        for row in reader:
            log.debug(f"Row: {row}")
            nid_int: int = int(row[nid_column])
            note: Note = mw.col.get_note(NoteId(nid_int))
            synonym1_old: str = note[synonym1_field]
            synonyms_old: str = note[synonyms_field]
            is_synonym1_empty: bool = synonym1_old.strip() == ''
            is_synonyms_empty: bool = synonyms_old.strip() == ''
            if note[word_field] != row[english_column]:
                raise RuntimeError(f"Wrong English word: note={note[word_field]}, row={row[english_column]}")
            if not is_synonym1_empty and not is_synonyms_empty:
                raise RuntimeError(f"Fields {synonym1_field} and {synonyms_field} are both not empty: "
                                   f"nid={note.id}, synonym1='{synonym1_old}, synonyms='{synonyms_old}'")
            full_synonyms: List[str] = [row[synonym_header] for synonym_header in synonym_headers
                                        if row[synonym_header]]
            if is_synonym1_empty:
                synonym1_new: str = full_synonyms[0] if len(full_synonyms) > 0 else ""
                if is_synonyms_empty:
                    if synonym1_new in full_synonyms:
                        full_synonyms.remove(synonym1_new)
                    synonyms_new: str = ", ".join(full_synonyms)
                else:
                    synonyms_new: str = synonyms_old
            else:
                synonym1_new: str = synonym1_old
                if is_synonyms_empty:
                    if synonym1_new in full_synonyms:
                        full_synonyms.remove(synonym1_new)
                    synonyms_new: str = ", ".join(full_synonyms)
                else:
                    synonyms_new: str = synonyms_old
            note[synonym1_field] = synonym1_new
            note[synonyms_field] = synonyms_new
            tags_old: List[str] = list(note.tags)
            if synonym1_new == '':
                note.tags.append(absent_synonym1_tag)
            else:
                if absent_synonym1_tag in tags_old:
                    note.tags.remove(absent_synonym1_tag)
            if synonyms_new == '':
                note.tags.append(absent_synonyms_tag)
            else:
                if absent_synonyms_tag in tags_old:
                    note.tags.remove(absent_synonyms_tag)
            log.info(f"Updating note: nid={note.id}, english='{note[word_field]}', "
                     f"pos='{note[part_of_speech_field]}', "
                     f"synonym1='{synonym1_old}'->'{synonym1_new}', "
                     f"synonyms='{synonyms_old}'->'{synonyms_new}', "
                     f"tags='{tags_old}'->'{note.tags}'")
            mw.col.update_note(note)


action = QAction('Load synonyms from OpenAI', mw)
qconnect(action.triggered, _fill)
mw.form.menuTools.addAction(action)

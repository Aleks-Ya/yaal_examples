import logging
from typing import List, Dict

from anki.notes import Note

from common.fields import english_field, part_of_speech_field, synonym1_field, synonyms_field, antonyms_field, \
    antonym1_field
from common.tags import absent_synonym1_tag, absent_synonyms_tag, absent_antonyms_tag, absent_antonym1_tag
from .columns import english_column, synonym_headers, antonym_headers

log: logging.Logger = logging.getLogger(__name__)


def fill_note(note: Note, row: Dict[str, str]):
    synonym1_old: str = note[synonym1_field]
    synonyms_old: str = note[synonyms_field]
    antonym1_old: str = note[antonym1_field]
    antonyms_old: str = note[antonyms_field]
    is_synonym1_empty: bool = synonym1_old.strip() == ''
    is_synonyms_empty: bool = synonyms_old.strip() == ''
    is_antonym1_empty: bool = antonym1_old.strip() == ''
    is_antonyms_empty: bool = antonyms_old.strip() == ''
    if note[english_field] != row[english_column]:
        raise RuntimeError(f"Wrong English word: note={note[english_field]}, row={row[english_column]}")
    if not is_synonym1_empty and not is_synonyms_empty and not is_antonym1_empty and not is_antonyms_empty:
        raise RuntimeError(
            f"Fields {synonym1_field}, {synonyms_field}, {is_antonym1_empty} and {antonyms_field} "
            f"are all not empty: nid={note.id}, synonym1='{synonym1_old}', synonyms='{synonyms_old}', "
            "antonym1='{antonym1_old}', antonyms='{antonyms_old}'")
    full_synonyms: List[str] = [row[synonym_header] for synonym_header in synonym_headers
                                if row[synonym_header]]
    full_antonyms: List[str] = [row[antonym_header] for antonym_header in antonym_headers
                                if row[antonym_header]]
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
    if is_antonym1_empty:
        antonym1_new: str = full_antonyms[0] if len(full_antonyms) > 0 else ""
        if is_antonyms_empty:
            if antonym1_new in full_antonyms:
                full_antonyms.remove(antonym1_new)
            antonyms_new: str = ", ".join(full_antonyms)
        else:
            antonyms_new: str = antonyms_old
    else:
        antonym1_new: str = antonym1_old
        if is_antonyms_empty:
            if antonym1_new in full_antonyms:
                full_antonyms.remove(antonym1_new)
            antonyms_new: str = ", ".join(full_antonyms)
        else:
            antonyms_new: str = antonyms_old
    note[synonym1_field] = synonym1_new
    note[synonyms_field] = synonyms_new
    note[antonym1_field] = antonym1_new
    note[antonyms_field] = antonyms_new

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
    if antonym1_new == '':
        note.tags.append(absent_antonym1_tag)
    else:
        if absent_antonym1_tag in tags_old:
            note.tags.remove(absent_antonym1_tag)
    if antonyms_new == '':
        note.tags.append(absent_antonyms_tag)
    else:
        if absent_antonyms_tag in tags_old:
            note.tags.remove(absent_antonyms_tag)

    log.info(f"Updating note: nid={note.id}, english='{note[english_field]}', "
             f"pos='{note[part_of_speech_field]}', "
             f"synonym1='{synonym1_old}'->'{synonym1_new}', "
             f"synonyms='{synonyms_old}'->'{synonyms_new}', "
             f"antonym1='{antonym1_old}'->'{antonym1_new}', "
             f"antonyms='{antonyms_old}'->'{antonyms_new}', "
             f"tags='{tags_old}'->'{note.tags}'")
    return note

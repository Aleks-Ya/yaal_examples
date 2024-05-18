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
    if note[english_field] != row[english_column]:
        raise RuntimeError(f"Wrong English word: note={note[english_field]}, row={row[english_column]}")
    if (not is_empty(synonym1_old) and not is_empty(synonyms_old) and
            not is_empty(antonym1_old) and not is_empty(antonyms_old)):
        raise RuntimeError(f"All fields are filled already: nid={note.id}, "
                           f"{synonym1_field}='{synonym1_old}', {synonyms_field}='{synonyms_old}', "
                           f"{antonym1_field}='{antonym1_old}', {antonyms_field}='{antonyms_old}'")
    full_synonyms: List[str] = [row[synonym_header] for synonym_header in synonym_headers if row[synonym_header]]
    full_antonyms: List[str] = [row[antonym_header] for antonym_header in antonym_headers if row[antonym_header]]

    synonym1_new, synonyms_new = update(full_synonyms, synonym1_old, synonyms_old)
    antonym1_new, antonyms_new = update(full_antonyms, antonym1_old, antonyms_old)

    note[synonym1_field] = synonym1_new
    note[synonyms_field] = synonyms_new
    note[antonym1_field] = antonym1_new
    note[antonyms_field] = antonyms_new

    tags_old: List[str] = list(note.tags)
    update_tags(note, synonym1_field, absent_synonym1_tag)
    update_tags(note, synonyms_field, absent_synonyms_tag)
    update_tags(note, antonym1_field, absent_antonym1_tag)
    update_tags(note, antonyms_field, absent_antonyms_tag)

    log.info(f"Updating note: nid={note.id}, english='{note[english_field]}', "
             f"pos='{note[part_of_speech_field]}', "
             f"synonym1='{synonym1_old}'->'{synonym1_new}', "
             f"synonyms='{synonyms_old}'->'{synonyms_new}', "
             f"antonym1='{antonym1_old}'->'{antonym1_new}', "
             f"antonyms='{antonyms_old}'->'{antonyms_new}', "
             f"tags='{tags_old}'->'{note.tags}'")
    return note


def remove_word(string: str, word: str) -> str:
    split = string.split(",")
    split = [s.strip() for s in split]
    split.remove(word)
    return ", ".join(split)


def remove_and_join(words: List[str], word: str) -> str:
    removed_list: List[str] = list(words)
    if word in removed_list:
        removed_list.remove(word)
    return ", ".join(removed_list)


def update(full_list: List[str], word1_old: str, words_old: str) -> (str, str):
    if is_empty(word1_old):
        word1_new: str = full_list[0] if len(full_list) > 0 else ""
        if is_empty(words_old):
            words_new: str = remove_and_join(full_list, word1_new)
        else:
            words_new: str = remove_word(words_old, word1_new)
    else:
        word1_new: str = word1_old
        if is_empty(words_old):
            words_new: str = remove_and_join(full_list, word1_new)
        else:
            words_new: str = words_old
    return word1_new, words_new


def is_empty(word: str) -> bool:
    return word.strip() == ''


def update_tags(note: Note, field: str, absent_tag: str) -> None:
    if is_empty(note[field]):
        note.tags.append(absent_tag)
    else:
        if absent_tag in note.tags:
            note.tags.remove(absent_tag)

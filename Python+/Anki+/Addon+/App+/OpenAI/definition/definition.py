import logging
from csv import DictReader
from io import StringIO
from typing import Sequence, List, Optional, Any

from anki.collection import OpChanges, Collection, OpChangesWithCount
from anki.notes import NoteId, Note
from aqt import mw
from aqt.operations import CollectionOp, ResultWithChanges
from aqt.qt import QAction, qconnect
from aqt.utils import showInfo
from openai.types.chat import ChatCompletion

from common.fields import english_field, definition_field
from common.part_of_speech import PartOfSpeech
from common.tags import unit_tag
from openai_client.openai_client import OpenAiClient

log: logging.Logger = logging.getLogger(__name__)


class WantCancelException(Exception):
    pass


class Definition:
    def __init__(self, config: Optional[dict[str, Any]]):
        self.openai_client: OpenAiClient = OpenAiClient(config)
        self.part_of_speech = PartOfSpeech()

    def _background_operation(self, col: Collection) -> ResultWithChanges:
        changes: OpChangesWithCount = OpChangesWithCount()
        query: str = f'{definition_field}: -tag:{unit_tag}'
        log.info(f"Query: '{query}'")
        note_ids: Sequence[NoteId] = col.find_notes(query)
        log.info(f"Found notes: {len(note_ids)}")
        notes: List[Note] = [col.get_note(note_id) for note_id in note_ids]
        slice_size: int = 25
        log.info(f"Slice size: {slice_size}")
        notes_len: int = len(notes)
        self._show_progress(changes, notes_len)
        for i in range(0, notes_len, slice_size):
            log.info(f"Processed notes: {i} from {notes_len}")
            sublist: List[Note] = notes[i:i + slice_size]
            try:
                self._update_notes(col, sublist, changes)
            except WantCancelException:
                log.info("Cancelling")
                break
            self._show_progress(changes, notes_len)
        return changes

    def _show_progress(self, changes: OpChangesWithCount, notes_len: int):
        mw.taskman.run_on_main(
            lambda: mw.progress.update(
                label=f"Updated: {changes.count} from {notes_len}",
                value=changes.count,
                max=notes_len
            )
        )

    def _update_notes(self, col: Collection, notes: List[Note], changes: OpChangesWithCount):
        log.info(f"Notes to update in slice: {len(notes)}")
        nid_column: str = 'ID'
        english_column: str = 'English Word'
        pos_column: str = 'Part Of Speech'
        definition_column: str = 'Definition'
        lines: List[str] = []
        for note in notes:
            try:
                word: str = note[english_field]
                pos: str = self.part_of_speech.tagsToSinglePos(note.tags)
                line: str = f'"{note.id}","{word}","{pos}"'
                lines.append(line)
            except Exception as e:
                raise Exception(f"Failed to process note: {note.id}") from e
        lines_str: str = '\n'.join(lines)
        prompt: str = (
            f'I will provide you a CSV snippet. '
            f'You need to fill column "{definition_column}" in the snippet with simple and short definition of the word. '
            f'Your response must contain strictly only raw CSV content (it is very important). '
            f'The CSV snippet is:\n'
            f'```\n'
            f'"{nid_column}","{english_column}","{pos_column}","{definition_column}"\n'
            f'{lines_str}\n'
            f'```'
        )
        log.debug(f"Prompt:\n{prompt}")
        chat_completion: ChatCompletion = self.openai_client.get_completion(prompt)
        message: str = chat_completion.choices[0].message.content
        log.debug(f"Message:\n{message}")
        message = message.replace('```\n', '').replace('\n```', '')
        log.debug(f"Message without MarkDown:\n{message}")
        with StringIO(message) as csv_file:
            reader: DictReader = DictReader(csv_file, doublequote=True)
            for row in reader:
                if mw.progress.want_cancel():
                    raise WantCancelException()
                log.debug(f"Row: {row}")
                nid_int: int = int(row[nid_column])
                note: Note = col.get_note(NoteId(nid_int))
                if note[english_field] != row[english_column]:
                    raise RuntimeError(f"Wrong English word: note={note[english_field]}, row={row[english_column]}")
                definition_old: str = note[definition_field]
                if definition_old != '':
                    raise RuntimeError(
                        f'Field {definition_field} is not empty: nid={nid_int}, value="{definition_old}"')
                definition_new: str = row[definition_column]
                if definition_new == '':
                    raise RuntimeError(f"Empty definition: nid={nid_int}")
                note[definition_field] = definition_new
                log.info(f"Updating note: nid={note.id}, english='{note[english_field]}', "
                         f"definition='{note[definition_field]}'")
                col.update_note(note)
                changes.count += 1

    def on_success(self, changes: ResultWithChanges) -> None:
        showInfo(f"Finished: updated {changes.count} notes")

    def on_failure(self, e: Exception) -> None:
        showInfo(f"Failed: {e}")

    def _ui_action(self):
        op: CollectionOp[OpChanges] = CollectionOp(parent=mw, op=lambda col: self._background_operation(col))
        op.success(self.on_success)
        op.failure(self.on_failure)
        op.run_in_background()

    def menu_action(self) -> QAction:
        action = QAction('Fill "Definition" field', mw)
        qconnect(action.triggered, self._ui_action)
        return action

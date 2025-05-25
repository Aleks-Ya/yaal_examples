# DO NOT USE: works slow, GPT responses are unreliable
import logging
from typing import Sequence, List

from anki.collection import OpChanges, Collection, OpChangesWithCount
from anki.notes import NoteId, Note
from aqt import mw
from aqt.operations import CollectionOp, ResultWithChanges
from aqt.qt import QAction, qconnect
from aqt.utils import showInfo

from .chinese_cleaner import ChineseCleaner
from common.config import LanguageAiConfig
from common.fields import english_field, examples1_generated_field, \
    examples2_generated_field, examples3_generated_field

log: logging.Logger = logging.getLogger(__name__)

fields: List[str] = [examples1_generated_field, examples2_generated_field, examples3_generated_field]


class Chinese:
    def __init__(self, config: LanguageAiConfig):
        self.cleaner: ChineseCleaner = ChineseCleaner(config)

    def __background_operation(self, col: Collection) -> ResultWithChanges:
        changes: OpChangesWithCount = OpChangesWithCount()
        field_clauses: List[str] = [f'{field}:_*' for field in fields]
        query: str = f'{" or ".join(field_clauses)}'
        log.info(f"Query: '{query}'")
        all_note_ids: Sequence[NoteId] = col.find_notes(query)
        log.info(f"Found all notes: {len(all_note_ids)}")
        all_notes: List[Note] = [col.get_note(note_id) for note_id in all_note_ids]
        notes: List[Note] = [note for note in all_notes if self.cleaner.note_contains_chinese(note)]
        notes_len: int = len(notes)
        log.info(f"Found chinese notes: {notes_len}")
        self.__show_progress(changes, notes_len)
        for note in notes:
            for field in fields:
                if mw.progress.want_cancel():
                    log.info("Cancelling")
                    return changes
                self.__update_note(col, note, field)
            changes.count += 1
            self.__show_progress(changes, notes_len)
        return changes

    @staticmethod
    def __show_progress(changes: OpChangesWithCount, notes_len: int):
        mw.taskman.run_on_main(
            lambda: mw.progress.update(
                label=f"Processed: {changes.count} from {notes_len}",
                value=changes.count,
                max=notes_len
            )
        )

    def __update_note(self, col: Collection, note: Note, field: str) -> None:
        note_id: NoteId = note.id
        log.info(f'Updating text: nid={note_id}, field={field}')
        old_value: str = note[field]
        english: str = note[english_field]
        clean_value: str = self.cleaner.clean_field(old_value, english, field, note_id)
        note[field] = clean_value
        log.info(f"Updating note: nid={note_id}, english='{english}', field='{field}',\n"
                 f"old='{old_value}',\n"
                 f"new='{note[field]}'")
        if old_value != clean_value:
            col.update_note(note)

    @staticmethod
    def on_success(changes: ResultWithChanges) -> None:
        showInfo(f"Finished: updated {changes.count} notes")

    @staticmethod
    def on_failure(e: Exception) -> None:
        showInfo(f"Failed: {e}")

    def _ui_action(self):
        op: CollectionOp[OpChanges] = CollectionOp(parent=mw, op=lambda col: self.__background_operation(col))
        op.success(self.on_success)
        op.failure(self.on_failure)
        op.run_in_background()

    def menu_action(self) -> QAction:
        action: QAction = QAction('Remove Chinese characters from examples', mw)
        qconnect(action.triggered, self._ui_action)
        return action

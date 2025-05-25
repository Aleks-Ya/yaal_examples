from logging import getLogger, Logger
from typing import Sequence, List

from anki.collection import Collection, OpChangesWithCount
from anki.collection_pb2 import OpChangesWithCount
from anki.notes import NoteId, Note
from aqt import mw, QWidget
from aqt.operations import CollectionOp

from .string_cleaner import StringCleaner

log: Logger = getLogger(__name__)


class CleanOperation(CollectionOp):
    def __init__(self, note_ids: Sequence[NoteId], parent: QWidget, dry_run: bool = True):
        super().__init__(parent=parent, op=self.__clean_notes)
        self.__note_ids: Sequence[NoteId] = note_ids
        self.__dry_run: bool = dry_run

    def __clean_notes(self, col: Collection) -> OpChangesWithCount:
        log.info(f"Cleaning fields in notes: dry_run={self.__dry_run}")
        changes: OpChangesWithCount = OpChangesWithCount()
        field_names: List[str] = [
            'Definition',
            'Definition-wq-generated',
            'Examples1-generated',
            'Examples2-generated',
            'Synonyms-generated',
            'Gpt4Short',
            'Gpt4Long',
            'Bard',
            'Quote',
            'Synonym1',
            'Synonyms',
            'Antonyms',
            'Answer',
            'Example-my',
            'Transcription',
            'Documentation',
            'Question'
        ]
        updated_notes_count: int = 0
        cleaner: StringCleaner = StringCleaner()
        log.info(f"Notes count: {len(self.__note_ids)}")
        for note_id in self.__note_ids:
            log.info(f"Processing note: {note_id}")
            note: Note = col.get_note(note_id)
            modified: bool = False
            for field_name in field_names:
                if field_name in note:
                    old_value: str = note[field_name]
                    new_value: str = cleaner.clean(old_value)
                    if new_value != old_value:
                        modified = True
                        log.info(f"Field {field_name} old value:\n{old_value}")
                        log.info(f"Field {field_name} new value:\n{new_value}")
                        note[field_name] = new_value
            if not self.__dry_run:
                if modified:
                    log.info(f"Updating note: {note_id}")
                    col.update_note(note)
            else:
                log.warning(f"Skipped update because dry_run={self.__dry_run}")
            changes.count += 1
            updated_notes_count += 1
            mw.taskman.run_on_main(
                lambda: mw.progress.update(label=f"Updated: {changes.count}")
            )
            if mw.progress.want_cancel():
                log.info(f"Cancelling")
                return changes
        return changes

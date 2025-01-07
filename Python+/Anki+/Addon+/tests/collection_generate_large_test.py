from pathlib import Path
from typing import Optional

from anki.collection import Collection, AddNoteRequest
from anki.decks import DeckId
from anki.models import NoteType
from anki.notes import Note


# Run with `-s` option to see console output immediately: `pytest -s`
def test_generate_large_collections(col: Collection, basic_note_type: NoteType, deck_id: DeckId):
    print(f"Collection path: {col.path}")
    note_count: int = 1000
    __generate_notes(col, deck_id, basic_note_type, note_count, front_prefix="normal", file_size_bytes=20,
                     files_in_field=1)
    note_with_missing_files_count: int = 100
    __generate_notes(col, deck_id, basic_note_type, note_with_missing_files_count, front_prefix="missing-file",
                     file_size_bytes=None, files_in_field=1)

    assert col.note_count() == note_count + note_with_missing_files_count
    profile_path: Path = Path(col.path).parent
    print(f"Profile path: {profile_path}")
    profile_size: int = sum(f.stat().st_size for f in profile_path.glob('**/*') if f.is_file())
    print(f"Profile size: {profile_size / 1024 / 1024:.2f} MB")


def __generate_notes(col: Collection, deck_id: DeckId, basic_note_type: NoteType, note_count: int,
                     front_prefix: str, file_size_bytes: Optional[int], files_in_field: int) -> None:
    file_content: Optional[str] = "a" * file_size_bytes if file_size_bytes is not None else None
    media_dir: Path = Path(col.media.dir())
    batch_size: int = 1000
    for start_index in range(0, note_count, batch_size):
        end_index: int = min(start_index + batch_size, note_count)
        requests: list[AddNoteRequest] = []
        for note_index in range(start_index, end_index):
            note: Note = col.new_note(basic_note_type)
            note['Front'] = f'{front_prefix} {note_index}'
            note['Back'] = 'back '
            for file_index in range(files_in_field):
                filename: str = f"picture-{front_prefix}-{note_index}-{file_index}.jpg"
                file: Path = media_dir.joinpath(filename)
                if file_content:
                    file.write_text(file_content)
                    col.media.add_file(file)
                note['Back'] += f' <img src="{filename}">'
            request: AddNoteRequest = AddNoteRequest(note, deck_id)
            requests.append(request)
        col.add_notes(requests)
        print(f"Added {end_index} notes of {note_count}")

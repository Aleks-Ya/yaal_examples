from anthropic import Anthropic, NotFoundError
from anthropic.pagination import SyncPageCursor
from anthropic.types import FileMetadata, DeletedFile
from datetime import datetime
from pytest import raises


def test_upload_file(client: Anthropic):
    metadata: FileMetadata = client.files.upload(
        file=("file.txt", b"my bytes", "text/plain")
    )
    print(metadata.model_dump_json())
    assert metadata.id is not None
    client.files.delete(file_id=metadata.id)


def test_upload_file_from_bytes(client: Anthropic):
    metadata: FileMetadata = client.files.upload(file=b"my bytes")
    assert metadata is not None
    client.files.delete(file_id=metadata.id)


def test_list_files(client: Anthropic):
    metadata_cursor: SyncPageCursor[FileMetadata] = client.files.list()
    metadata_list: list[FileMetadata] = [metadata for metadata in metadata_cursor]
    for metadata in metadata_list:
        print(metadata.model_dump_json())
    assert len(metadata_list) > 0


def test_delete_file(client: Anthropic):
    metadata: FileMetadata = client.files.upload(file=("file.txt", b"my bytes", "text/plain"))
    file_id: str = metadata.id
    assert client.files.retrieve_metadata(file_id=file_id) is not None

    deleted_file: DeletedFile = client.files.delete(file_id=file_id)
    assert deleted_file.id == file_id
    with raises(NotFoundError):
        client.files.retrieve_metadata(file_id=file_id)


def test_get_file_metadata(client: Anthropic):
    metadata_exp: FileMetadata = client.files.upload(file=("file.txt", b"my bytes", "text/plain"))
    file_id: str = metadata_exp.id
    metadata_act = client.files.retrieve_metadata(file_id=file_id)
    assert metadata_act == metadata_exp
    client.files.delete(file_id=file_id)


def test_expires_in_seconds(client: Anthropic):
    metadata: FileMetadata = client.files.upload(
        file=b"my bytes",
        expires_in_seconds=3600
    )
    expires: datetime | None = metadata.expires_at
    assert expires is not None

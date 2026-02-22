from pathlib import Path

from python_postman import PythonPostman, Collection

from current_path import get_file_in_current_dir


def test_create_collection():
    collection: Collection = PythonPostman.create_collection("collection1")
    assert collection.info.name == "collection1"


def test_read_collection_from_file():
    file: Path = get_file_in_current_dir("Postman.postman_collection.json")
    collection: Collection = PythonPostman.from_file(file)
    requests: list[str] = collection.list_requests()
    print(requests)
    assert "Get request" in requests


def test_read_collection_from_json():
    json_string: str = '{"info": {"name": "My Collection"}, "item": []}'
    collection: Collection = PythonPostman.from_json(json_string)
    assert collection.info.name == "My Collection"


def test_read_collection_from_dict():
    collection_dict: dict = {"info": {"name": "My Collection"}, "item": []}
    collection: Collection = PythonPostman.from_dict(collection_dict)
    assert collection.info.name == "My Collection"

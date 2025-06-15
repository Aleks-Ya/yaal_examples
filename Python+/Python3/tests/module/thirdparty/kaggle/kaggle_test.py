import tempfile
from pathlib import Path

import kaggle
from kagglesdk.datasets.types.dataset_api_service import ApiCreateDatasetResponse


def test_metadata():
    out_dir: str = tempfile.mkdtemp()
    metadata_file: str = kaggle.api.dataset_metadata('alexeyya/example-dataset-1', path=out_dir)
    print(metadata_file)
    assert metadata_file == out_dir + "/dataset-metadata.json"
    assert Path(metadata_file).exists()


def test_create_version():
    folder: str = "/home/aleks/pr/home/yaal_examples/CLI/Kaggle/example-dataset-1/dataset"
    version_notes: str = "Update"
    response: ApiCreateDatasetResponse = kaggle.api.dataset_create_version(folder=folder, version_notes=version_notes)
    print(response)
    assert response.status == "Ok"

from pathlib import Path

import pytest


@pytest.fixture
def onnx_model_file() -> Path:
    return Path.home() / "models/OpenSearch/sentence-transformers_paraphrase-mpnet-base-v2-1.0.0-onnx/paraphrase-mpnet-base-v2.onnx"

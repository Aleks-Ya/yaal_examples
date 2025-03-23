from pathlib import Path

from numpy import ndarray
from onnxruntime import InferenceSession
import numpy


def test_onnx_runtime():
    model_file: Path = Path.home() / "models/OpenSearch/sentence-transformers_paraphrase-mpnet-base-v2-1.0.0-onnx/paraphrase-mpnet-base-v2.onnx"
    session: InferenceSession = InferenceSession(model_file)
    assert session.get_providers() == ['CPUExecutionProvider']
    input_ids: ndarray = numpy.array([[101, 7592, 999, 102], [101, 7592, 2023, 102]], dtype=numpy.int64)
    attention_mask: ndarray = numpy.array([[1, 1, 1, 1], [1, 1, 1, 1]], dtype=numpy.int64)
    outputs: object = session.run(None, {"input_ids": input_ids, "attention_mask": attention_mask})
    print(outputs)
    assert outputs is not None

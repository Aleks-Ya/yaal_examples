from pathlib import Path

from numpy import ndarray
from onnxruntime import InferenceSession
import numpy


def test_onnx_runtime(onnx_model_file: Path):
    session: InferenceSession = InferenceSession(onnx_model_file)
    assert session.get_providers() == ['CPUExecutionProvider']
    input_ids: ndarray = numpy.array([[101, 7592, 999, 102], [101, 7592, 2023, 102]], dtype=numpy.int64)
    attention_mask: ndarray = numpy.array([[1, 1, 1, 1], [1, 1, 1, 1]], dtype=numpy.int64)
    input_feed: dict[str, ndarray] = {"input_ids": input_ids, "attention_mask": attention_mask}
    outputs: list[list[list[list[float]]]] = session.run(None, input_feed)
    assert len(outputs[0][0][0]) == 768
    print(outputs)

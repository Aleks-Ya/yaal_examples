from pathlib import Path

from numpy import ndarray
from onnxruntime import InferenceSession
import numpy


def test_onnx_runtime(onnx_model_file: Path):
    session: InferenceSession = InferenceSession(onnx_model_file, providers=['OpenVINOExecutionProvider'])
    assert session.get_providers() == ['OpenVINOExecutionProvider', 'CPUExecutionProvider']
    input_ids: ndarray = numpy.array([[101, 7592, 999, 102], [101, 7592, 2023, 102]], dtype=numpy.int64)
    attention_mask: ndarray = numpy.array([[1, 1, 1, 1], [1, 1, 1, 1]], dtype=numpy.int64)
    input_feed: dict[str, ndarray] = {"input_ids": input_ids, "attention_mask": attention_mask}
    outputs: list[list[list[list[float]]]] = session.run(None, input_feed)
    assert len(outputs[0][0][0]) == 768
    print(outputs)


def test_onnx_runtime_performance(onnx_model_file: Path):
    session: InferenceSession = InferenceSession(onnx_model_file, providers=['OpenVINOExecutionProvider'])
    assert session.get_providers() == ['OpenVINOExecutionProvider', 'CPUExecutionProvider']
    attention_mask: ndarray = numpy.array([[1, 1, 1, 1], [1, 1, 1, 1]], dtype=numpy.int64)
    for i in range(1000):
        input_ids: ndarray = numpy.array(
            [[101 + i, 7592 + i, 999 + i, 102 + i], [101 + i, 7592 + i, 2023 + i, 102 + i]], dtype=numpy.int64)
        input_feed: dict[str, ndarray] = {"input_ids": input_ids, "attention_mask": attention_mask}
        outputs: list[list[list[list[float]]]] = session.run(None, input_feed)
        assert len(outputs[0][0][0]) == 768


def test_use_gpu_provider(onnx_model_file: Path):
    providers: list[tuple[str, dict[str, str]]] = [("OpenVINOExecutionProvider", {'device_type': 'GPU'})]
    session: InferenceSession = InferenceSession(onnx_model_file, providers=providers)
    available_devices: list[str] = session.get_providers()
    assert available_devices == ['CPUExecutionProvider']  # fallback to CPU

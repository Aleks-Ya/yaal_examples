from pathlib import Path

from numpy import ndarray
from onnxruntime import InferenceSession
import numpy


def test_onnx_runtime(onnx_model_file: Path):
    session: InferenceSession = InferenceSession(onnx_model_file, providers=['OpenVINOExecutionProvider'])
    assert session.get_providers() == ['OpenVINOExecutionProvider', 'CPUExecutionProvider']
    input_ids: ndarray = numpy.array([[101, 7592, 999, 102], [101, 7592, 2023, 102]], dtype=numpy.int64)
    attention_mask: ndarray = numpy.array([[1, 1, 1, 1], [1, 1, 1, 1]], dtype=numpy.int64)
    outputs: object = session.run(None, {"input_ids": input_ids, "attention_mask": attention_mask})
    print(outputs)
    assert outputs is not None


def test_onnx_runtime_performance(onnx_model_file: Path):
    session: InferenceSession = InferenceSession(onnx_model_file, providers=['OpenVINOExecutionProvider'])
    assert session.get_providers() == ['OpenVINOExecutionProvider', 'CPUExecutionProvider']
    input_ids: ndarray = numpy.array([[101, 7592, 999, 102], [101, 7592, 2023, 102]], dtype=numpy.int64)
    attention_mask: ndarray = numpy.array([[1, 1, 1, 1], [1, 1, 1, 1]], dtype=numpy.int64)
    for i in range(1000):
        outputs: object = session.run(None, {"input_ids": input_ids, "attention_mask": attention_mask})
        print(outputs)
        assert outputs is not None


def test_use_gpu_provider(onnx_model_file: Path):
    providers: list[tuple[str, dict[str, str]]] = [("OpenVINOExecutionProvider", {'device_type': 'GPU'})]
    session: InferenceSession = InferenceSession(onnx_model_file, providers=providers)
    available_devices: list[str] = session.get_providers()
    assert available_devices == ['CPUExecutionProvider']  # fallback to CPU

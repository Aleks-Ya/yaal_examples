from pathlib import Path

import onnx
from onnx import ModelProto


def test_check_model_proto(onnx_model_file: Path):
    model: ModelProto = onnx.load(onnx_model_file)
    onnx.checker.check_model(model)


def test_check_model_path(onnx_model_file: Path):
    onnx.checker.check_model(onnx_model_file)

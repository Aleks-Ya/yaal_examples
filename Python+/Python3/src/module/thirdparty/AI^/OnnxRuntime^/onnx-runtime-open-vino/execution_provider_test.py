import onnxruntime


def test_get_available_providers():
    providers: list[str] = onnxruntime.get_available_providers()
    assert providers == ['OpenVINOExecutionProvider', 'CPUExecutionProvider']

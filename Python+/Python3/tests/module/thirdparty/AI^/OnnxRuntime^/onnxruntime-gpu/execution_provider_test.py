import onnxruntime


def test_get_available_providers():
    providers: list[str] = onnxruntime.get_available_providers()
    assert providers == ['TensorrtExecutionProvider', 'CUDAExecutionProvider', 'CPUExecutionProvider']


def test_get_all_providers():
    providers: list[str] = onnxruntime.get_all_providers()
    assert providers == ['TensorrtExecutionProvider', 'CUDAExecutionProvider', 'MIGraphXExecutionProvider',
                         'ROCMExecutionProvider', 'OpenVINOExecutionProvider', 'DnnlExecutionProvider',
                         'VitisAIExecutionProvider', 'QNNExecutionProvider', 'NnapiExecutionProvider',
                         'VSINPUExecutionProvider', 'JsExecutionProvider', 'CoreMLExecutionProvider',
                         'ArmNNExecutionProvider', 'ACLExecutionProvider', 'DmlExecutionProvider',
                         'RknpuExecutionProvider', 'WebNNExecutionProvider', 'WebGpuExecutionProvider',
                         'XnnpackExecutionProvider', 'CANNExecutionProvider', 'AzureExecutionProvider',
                         'CPUExecutionProvider']


def test_get_device():
    device: str = onnxruntime.get_device()
    print(device)
    assert device == "GPU"

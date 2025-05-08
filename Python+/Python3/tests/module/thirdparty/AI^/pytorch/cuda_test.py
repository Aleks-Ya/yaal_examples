import torch


def test_is_available():
    assert not torch.cuda.is_available()


def test_cudnn():
    assert torch.backends.cudnn.is_available()
